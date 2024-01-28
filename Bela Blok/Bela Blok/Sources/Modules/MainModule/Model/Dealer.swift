import Foundation

enum Dealer: CaseIterable, Codable {
    case me
    case rightOpponent
    case partner
    case leftOpponent

    var description: String {
        switch self {
        case .me:
            return "Ja"
        case .rightOpponent:
            return "Desni protivnik"
        case .partner:
            return "Partner"
        case .leftOpponent:
            return "Lijevi protivnik"
        }
    }

    var nextDealer: Dealer {
        switch self {
        case .me:
            return .rightOpponent
        case .rightOpponent:
            return .partner
        case .partner:
            return .leftOpponent
        case .leftOpponent:
            return .me
        }
    }
    
    var previousDealer: Dealer {
        switch self {
        case .me:
            return .leftOpponent
        case .rightOpponent:
            return .me
        case .partner:
            return .rightOpponent
        case .leftOpponent:
            return .partner
        }
    }
}
