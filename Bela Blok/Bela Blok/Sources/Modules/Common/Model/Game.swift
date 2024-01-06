import Foundation

struct Game: Codable, Hashable, Identifiable {
    var id = UUID()
    
    var weCall20: Int = 0
    var weCall50: Int = 0
    var weCall100: Int = 0
    var weCallBelot: Int = 0

    var youCall20: Int = 0
    var youCall50: Int = 0
    var youCall100: Int = 0
    var youCallBelot: Int = 0
    
    var caller: Caller = .we

    var weBaseScore: Int = -1
    var youBaseScore: Int = -1
    
    public var weCallsSum: Int {
        weCall20 * 20 + weCall50 * 50 + weCall100 * 100 + weCallBelot * 1001
    }
    
    public var youCallsSum: Int {
        youCall20 * 20 + youCall50 * 50 + youCall100 * 100 + youCallBelot * 1001
    }
    
    public var weTotal: Int {
        weCallsSum + weBaseScore
    }
    
    public var youTotal: Int {
        youCallsSum + youBaseScore
    }
}

extension [Game] {
    var id: UUID {
        UUID()
    }
    
    var weTotalAccumulated: Int {
        self.map(\.weTotal).reduce(0, +)
    }
    
    var youTotalAccumulated: Int {
        self.map(\.youTotal).reduce(0, +)
    }
    
    var weBaseGame: Int {
        self.map(\.weBaseScore).reduce(0, +)
    }
    
    var youBaseGame: Int {
        self.map(\.youBaseScore).reduce(0, +)
    }
    
    var weCallsSum: Int {
        self.map(\.weCallsSum).reduce(0, +)
    }
    
    var youCallsSum: Int {
        self.map(\.youCallsSum).reduce(0, +)
    }
}
