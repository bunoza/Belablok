import Foundation
import SwiftUI

class StatsViewModel: ObservableObject {
    @Published var game: [Game]
    @Published var weGraphData: [GraphData] = [GraphData(amount: 0, orderedNumber: 0)]
    @Published var youGraphData: [GraphData] = [GraphData(amount: 0, orderedNumber: 0)]
    @Published var stats: Image?
    @Published var graph: Image?
    @Published var list: Image?
    @Published var all: Image?

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
        var iterator = 1
        for match in game {
            weGraphData.append(GraphData(amount: match.weTotal + (weGraphData.last?.amount ?? 0), orderedNumber: iterator))
            youGraphData.append(GraphData(amount: match.youTotal + (youGraphData.last?.amount ?? 0), orderedNumber: iterator))
            iterator += 1
        }
    }

    @MainActor
    func onAppear(_ stats: some View, _ graph: some View, _ list: some View) async {
        self.stats = stats.snapshot()
        self.graph = graph.snapshot()
        self.list = list.snapshot()
    }
    
    func getOrderedNumberOfGame(_ searchedGame: Game) -> Int? {
        guard let index = game.firstIndex(of: searchedGame) else { return nil }
        return index + 1
    }
}

struct GraphData: Identifiable, Hashable {
    var id = UUID()

    let amount: Int
    let orderedNumber: Int
}
