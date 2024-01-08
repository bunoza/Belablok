import Foundation
import SwiftUI

extension View {
    @MainActor
    func snapshot() -> Image {
        let renderer = ImageRenderer(content: self)
        renderer.scale = 3.0
        return Image(uiImage: renderer.uiImage ?? UIImage())
    }
    
    func hiddenNavigationLink<DestinationView: View>(
        isLinkActive: Binding<Bool>,
        destination: DestinationView
    ) -> some View {
        background(
            NavigationLink(destination: destination, isActive: isLinkActive) {
                EmptyView()
            }
                .isDetailLink(false)
                .hidden()
        )
    }

//    func setGreenBackground() -> some View {
//        ZStack {
//            Color.green.opacity(0.7).ignoresSafeArea(.all)
//            self
//        }
//    }
}
