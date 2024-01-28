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
    var dealer: Dealer?

    var weBaseScore: Int = -1
    var youBaseScore: Int = -1

    var didFallIndicator: Bool = false
    
    
    mutating func resetWeCalls() {
        weCall20 = 0
        weCall50 = 0
        weCall100 = 0
        weCallBelot = 0
    }

    mutating func resetYouCalls() {
        youCall20 = 0
        youCall50 = 0
        youCall100 = 0
        youCallBelot = 0
    }

    mutating func resetCalls() {
        resetWeCalls()
        resetYouCalls()
    }
    
    private mutating func transferCalls(to: Caller) {
        switch to {
        case .we:
            weCall20 += youCall20
            weCall50 += youCall50
            weCall100 += youCall100
            weCallBelot += youCallBelot
            
            youCall20 = 0
            youCall50 = 0
            youCall100 = 0
            youCallBelot = 0
        case .you:
            youCall20 += weCall20
            youCall50 += weCall50
            youCall100 += weCall100
            youCallBelot += weCallBelot
            
            weCall20 = 0
            weCall50 = 0
            weCall100 = 0
            weCallBelot = 0
        }
    }
    
    var handleFall: Game {
        var gameForDisplay = self
        switch gameForDisplay.caller {
        case .we:
            if gameForDisplay.weTotal <= gameForDisplay.youTotal {
                gameForDisplay.youBaseScore = 162
                gameForDisplay.weBaseScore = 0
                gameForDisplay.transferCalls(to: .you)
                gameForDisplay.didFallIndicator = true
            }
        case .you:
            if gameForDisplay.youTotal <= gameForDisplay.weTotal {
                gameForDisplay.weBaseScore = 162
                gameForDisplay.youBaseScore = 0
                gameForDisplay.transferCalls(to: .we)
                gameForDisplay.didFallIndicator = true
            }
        }
        return gameForDisplay
    }

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

    var weFallCount: Int {
        self.filter { $0.caller == .we }
            .filter { $0.didFallIndicator == true }
            .count
    }

    var youFallCount: Int {
        self.filter { $0.caller == .you }
            .filter { $0.didFallIndicator == true }
            .count
    }
    
    var forDisplay: [Game] {
        self.map(\.handleFall)
    }
}
