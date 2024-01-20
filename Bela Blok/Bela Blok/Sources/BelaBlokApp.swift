import Combine
import Firebase
import SwiftUI

@main
struct BelaBlokApp: App {
    @Environment(\.scenePhase) var scenePhase
    @Environment(\.colorScheme) private var colorScheme
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate

    private var appState: AppState = .shared

    init() {
        FirebaseApp.configure()
    }

    var body: some Scene {
        WindowGroup {
            MainView()
        }
        .onChange(of: scenePhase) { phase in
            switch phase {
            case .active:
                UIApplication.shared.isIdleTimerDisabled = appState.isIdleTimerDisabled
            case .inactive: break
            case .background: break
            @unknown default: print("ScenePhase: unexpected state")
            }
        }
    }
}