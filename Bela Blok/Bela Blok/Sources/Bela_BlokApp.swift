import SwiftUI
import Firebase

@main
struct Bela_BlokApp: App {
    init() {
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            MainView()
        }
    }
}
