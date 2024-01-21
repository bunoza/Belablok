
import Foundation

enum ContinueGameOption: CaseIterable, Equatable, Identifiable {
    var id: UUID { UUID() }
    case scan
    case show

    var description: String {
        switch self {
        case .scan:
            "Nastavi na ovom uređaju"
        case .show:
            "Nastavi s ovog uređaja"
        }
    }
}
