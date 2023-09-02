import Foundation

struct Game {
    public var weScoreString: String = "0"
    public var youScoreString: String = "0"

    var weCall20: Int = 0
    var weCall50: Int = 0
    var weCall100: Int = 0
    var weCallBelot: Int = 0

    var youCall20: Int = 0
    var youCall50: Int = 0
    var youCall100: Int = 0
    var youCallBelot: Int = 0
    
    var caller: Caller = .we

    var weScore: Int = 0 {
        didSet {
            weScoreString = String(weScore)
        }
    }
    
    var youScore: Int = 0 {
        didSet {
            youScoreString = String(youScore)
        }
    }
    
    public func getWeCallsSum() -> Int {
        return weCall20 * 20 + weCall50 * 50 + weCall100 * 100 + weCallBelot * 1001
    }
    
    public func getYouCallsSum() -> Int {
        return youCall20 * 20 + youCall50 * 50 + youCall100 * 100 + youCallBelot * 1001
    }
}

enum Caller: CaseIterable {
    case we
    case you
    
    var description: String {
        switch self {
        case .we:
            return "Mi smo zvali"
        case .you:
            return "Vi ste zvali"
        }
    }
}
