import Foundation

enum Caller: CaseIterable, Codable {
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
