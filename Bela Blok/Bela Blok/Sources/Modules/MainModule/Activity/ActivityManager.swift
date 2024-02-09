import ActivityKit
import Combine
import Foundation

final class ActivityManager: ObservableObject {
    @MainActor @Published private(set) var activityID: String?
    @MainActor @Published private(set) var activityToken: String?
    
    static let shared = ActivityManager()
    
    func start(wePoints: Int, youPoints: Int) async {
        await endActivity()
        await startNewLiveActivity(wePoints: wePoints, youPoints: youPoints)
    }
    
    private func startNewLiveActivity(wePoints: Int, youPoints: Int) async {
        let attributes = BelaBlokWidgetAttributes()
        
        let initialContentState = ActivityContent(
            state: BelaBlokWidgetAttributes.ContentState(wePoints: wePoints, youPoints: youPoints),
            staleDate: nil
        )
        
        let activity = try? Activity.request(
            attributes: attributes,
            content: initialContentState,
            pushType: .token
        )
        
        guard let activity = activity else {
            return
        }
        await MainActor.run { activityID = activity.id }
        
        for await data in activity.pushTokenUpdates {
            let token = data.map {String(format: "%02x", $0)}.joined()
            print("Activity token: \(token)")
            await MainActor.run { activityToken = token }
            // HERE SEND THE TOKEN TO THE SERVER
        }
    }
    
    func updateActivity(wePoints: Int, youPoints: Int) async {
        guard let activityID = await activityID,
              let runningActivity = Activity<BelaBlokWidgetAttributes>.activities.first(where: { $0.id == activityID }) else {
            return
        }
        
        let newContentState = BelaBlokWidgetAttributes.ContentState(
            wePoints: wePoints,
            youPoints: youPoints
        )
        
        await runningActivity.update(using: newContentState)
    }
    
    func endActivity() async {
        guard let activityID = await activityID,
              let runningActivity = Activity<BelaBlokWidgetAttributes>.activities.first(where: { $0.id == activityID }) else {
            return
        }
        let initialContentState = BelaBlokWidgetAttributes.ContentState(wePoints: 0, youPoints: 0)

        await runningActivity.end(
            ActivityContent(state: initialContentState, staleDate: Date.distantFuture),
            dismissalPolicy: .immediate
        )
        
        await MainActor.run {
            self.activityID = nil
            self.activityToken = nil
        }
    }
    
    func cancelAllRunningActivities() async {
        for activity in Activity<BelaBlokWidgetAttributes>.activities {
            let initialContentState = BelaBlokWidgetAttributes.ContentState(wePoints: 0, youPoints: 0)
            
            await activity.end(
                ActivityContent(state: initialContentState, staleDate: Date()),
                dismissalPolicy: .immediate
            )
        }
        
        await MainActor.run {
            activityID = nil
            activityToken = nil
        }
    }
}
