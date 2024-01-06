import Foundation

class HistoryViewModel: ObservableObject {
    @Published var history: [[Game]] = []
    
    init() {
        let appState = AppState.shared
        history = appState.finishedGames
    }
    
    
    func delete(_ games: [Game]) {
        if let index = history.firstIndex(of: games) {
            history.remove(at: index)
            AppState.shared.finishedGames = history
        }
    }
    
    func getOrderedNumberOfGame(_ games: [Game]) -> Int? {
        guard let index = history.firstIndex(of: games) else { return nil }
        return index + 1
    }
}
