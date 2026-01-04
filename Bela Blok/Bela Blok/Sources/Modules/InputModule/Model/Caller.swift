import Foundation

enum Caller: CaseIterable, Codable {
    case we
    case you
    case none

    var description: String {
        switch self {
        case .we:
            return "Mi smo zvali"
        case .you:
            return "Vi ste zvali"
        case .none:
            return ""
        }
    }

    var name: String {
        switch self {
        case .we:
            return "Mi"
        case .you:
            return "Vi"
        case .none:
            return "Ništa"
        }
    }
}
