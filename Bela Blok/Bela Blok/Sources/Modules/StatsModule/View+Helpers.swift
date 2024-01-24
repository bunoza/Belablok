import Foundation
import SwiftUI

extension View {
    @MainActor
    func snapshot() -> Image {
        let renderer = ImageRenderer(content: self)
        renderer.scale = 3.0
        return Image(uiImage: renderer.uiImage ?? UIImage())
    }
}
