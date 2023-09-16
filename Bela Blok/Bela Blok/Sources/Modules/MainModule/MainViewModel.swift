import Foundation

class MainViewModel: ObservableObject {
    @Published var currentGame: [Game] = []
    
    func onAppear() {
        self.currentGame = AppState.shared.currentGame
    }
}
