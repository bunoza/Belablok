import Foundation

class StatsViewModel: ObservableObject {
    @Published var game: [Game]
    @Published var weGraphData: [GraphData] = [GraphData(amount: 0, orderedNumber: 0)]
    @Published var youGraphData: [GraphData] = [GraphData(amount: 0, orderedNumber: 0)]

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
        var i = 1
        for match in game {
            weGraphData.append(GraphData(amount: match.weTotal + (weGraphData.last?.amount ?? 0), orderedNumber: i))
            youGraphData.append(GraphData(amount: match.youTotal + (youGraphData.last?.amount ?? 0), orderedNumber: i))
            i += 1
        }
    }
}

struct GraphData: Identifiable, Hashable {
    var id = UUID()
    
    let amount: Int
    let orderedNumber: Int
}
