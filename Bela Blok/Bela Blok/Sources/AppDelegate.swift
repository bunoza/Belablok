import UIKit
import UserNotifications

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions _: [
            UIApplication.LaunchOptionsKey: Any
        ]?
    ) -> Bool {
        Task {
            let center = UNUserNotificationCenter.current()
            let authorizationStatus = await center
                .notificationSettings().authorizationStatus

            if authorizationStatus == .authorized {
                await MainActor.run {
                    application.registerForRemoteNotifications()
                }
            }
        }
        return true
    }

    func application(
        _: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        let token = deviceToken.reduce("") { $0 + String(format: "%02x", $1) }
        print("device token: \(token)")

        // send the token to your server
    }
}
