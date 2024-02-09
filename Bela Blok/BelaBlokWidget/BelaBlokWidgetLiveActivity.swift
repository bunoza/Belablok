import ActivityKit
import WidgetKit
import SwiftUI

struct BelaBlokWidgetAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        var wePoints: Int
        var youPoints: Int
        
        init(wePoints: Int, youPoints: Int) {
            self.wePoints = wePoints
            self.youPoints = youPoints
        }
    }
}

struct BelaBlokWidgetLiveActivity: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: BelaBlokWidgetAttributes.self) { context in
            LiveActivityView(state: context.state)
        } dynamicIsland: { context in
            DynamicIsland {
                DynamicIslandExpandedRegion(.leading) {
                    VStack {
                        Text("MI").bold()
                        Text("\(context.state.wePoints)")
                    }
                    .padding()
                }
                DynamicIslandExpandedRegion(.trailing) {
                    VStack {
                        Text("VI").bold()
                        Text("\(context.state.youPoints)")
                    }
                    .padding()
                }
                DynamicIslandExpandedRegion(.center) {
                    Image("LaunchScreenImage")
                        .resizable()
                        .scaledToFit()
                }
                DynamicIslandExpandedRegion(.bottom) {
                    VStack {
                        let diff = context.state.wePoints - context.state.youPoints
                        if context.state.wePoints > context.state.youPoints {
                            Text("Mi vodimo \(diff < 0 ? diff * -1 : diff) razlike.")
                        } else if context.state.youPoints > context.state.wePoints {
                            Text("Vi vodite \(diff < 0 ? diff * -1 : diff) razlike.")
                        } else {
                            Text("Izjednačeno je.")
                        }
                    }
                    .padding()
                }
            } compactLeading: {
                Text("\(context.state.wePoints)")
            } compactTrailing: {
                Text("\(context.state.youPoints)")
            } minimal: {
                Image("LaunchScreenImage")
            }
            .keylineTint(Color(.defaultBackground))
        }
    }
}

struct LiveActivityView: View {
    
    let state: BelaBlokWidgetAttributes.ContentState
    
    var body: some View {
        ZStack {
            Color(.defaultBackground)
                .opacity(0.8)
                .ignoresSafeArea()
            HStack {
                VStack {
                    Text("MI").bold()
                    Text("\(state.wePoints)")
                }
                .padding()
                
                VStack {
                    let diff = state.wePoints - state.youPoints
                    if state.wePoints > state.youPoints {
                        Text("Mi vodimo \(diff < 0 ? diff * -1 : diff) razlike.")
                    } else if state.youPoints > state.wePoints {
                        Text("Vi vodite \(diff < 0 ? diff * -1 : diff) razlike.")
                    } else {
                        Text("Izjednačeno je.")
                    }
                }
                .padding()
                
                VStack {
                    Text("VI").bold()
                    Text("\(state.youPoints)")
                }
                .padding()
            }
        }
    }
}

extension BelaBlokWidgetAttributes {
    fileprivate static var preview: BelaBlokWidgetAttributes {
        BelaBlokWidgetAttributes()
    }
}

extension BelaBlokWidgetAttributes.ContentState {
    fileprivate static var youLead: BelaBlokWidgetAttributes.ContentState {
        BelaBlokWidgetAttributes.ContentState(wePoints: 160, youPoints: 200)
     }
     
     fileprivate static var weLead: BelaBlokWidgetAttributes.ContentState {
         BelaBlokWidgetAttributes.ContentState(wePoints: 200, youPoints: 160)
     }
}

#Preview("Notification", as: .content, using: BelaBlokWidgetAttributes.preview) {
   BelaBlokWidgetLiveActivity()
} contentStates: {
    BelaBlokWidgetAttributes.ContentState.youLead
    BelaBlokWidgetAttributes.ContentState.weLead
}
