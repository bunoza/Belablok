import Foundation

enum StigljaValue: CaseIterable, Codable {
    case ninety
    case hundred

    var amount: Int {
        switch self {
        case .ninety:
            return 90
        case .hundred:
            return 100
        }
    }
}
