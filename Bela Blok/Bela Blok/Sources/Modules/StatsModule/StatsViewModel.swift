import Foundation

class StatsViewModel: ObservableObject {
    @Published var game: [Game]
    
    var weTotal: Int {
        game.weTotalAccumulated
    }
    
    var weNumberOfCalls: Int {
        game.filter { $0.caller == .we }.count
    }
    
    var youTotal: Int {
        game.youTotalAccumulated
    }
    
    var youNumberOfCalls: Int {
        game.filter { $0.caller == .you }.count
    }

    init(game: [Game]) {
        self.game = game
    }
}
