import Foundation

class HistoryViewModel: ObservableObject {
    @Published var history: [[Game]] = []
    
    func onAppear() {
        history = AppState.shared.finishedGames
    }
}
