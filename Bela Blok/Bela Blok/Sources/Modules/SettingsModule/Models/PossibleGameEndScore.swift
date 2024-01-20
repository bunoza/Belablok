import Foundation

enum PossibleGameEndScore: CaseIterable, Codable {
    case small
    case medium
    case large

    var amount: Int {
        switch self {
        case .small:
            return 501
        case .medium:
            return 701
        case .large:
            return 1001
        }
    }
}
