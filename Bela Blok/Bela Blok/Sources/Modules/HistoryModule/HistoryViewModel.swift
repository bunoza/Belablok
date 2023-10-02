import Foundation

class HistoryViewModel: ObservableObject {
    @Published var history: [[Game]] = []
    
    init() {
        let appState = AppState.shared
        history = appState.finishedGames
    }
    
    
    func delete(at offsets: IndexSet) {
        history.remove(atOffsets: offsets)
        AppState.shared.finishedGames = history
    }
}
