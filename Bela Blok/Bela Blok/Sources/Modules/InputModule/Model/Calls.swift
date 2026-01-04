import Foundation

enum Calls: Int, CaseIterable, Codable {
    case dvadeset = 20
    case pedeset = 50
    case sto = 100
    case stopedeset = 150
    case dvjesto = 200
    case belot = 1001

    var description: String {
        switch self {
        case .dvadeset:
            "20"
        case .pedeset:
            "50"
        case .sto:
            "100"
        case .stopedeset:
            "150"
        case .dvjesto:
            "200"
        case .belot:
            "Belot"
        }
    }
}
