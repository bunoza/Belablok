import Foundation

class StatsViewModel: ObservableObject {
    @Published var game: [Game]
    
    init(game: [Game]) {
        self.game = game
    }
}
