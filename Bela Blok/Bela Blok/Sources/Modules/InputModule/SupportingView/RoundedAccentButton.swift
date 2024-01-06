import SwiftUI

struct RoundedAccentButton: ViewModifier {
    private let width: CGFloat
    private let height: CGFloat
    
    init(width: CGFloat, height: CGFloat) {
        self.width = width
        self.height = height
    }
    
    func body(content: Content) -> some View {
        content
            .font(.title)
            .padding()
            .background(
                RoundedRectangle(
                    cornerRadius: 7,
                    style: .circular
                )
                .stroke(.primary, lineWidth: 1)
                .shadow(radius: 2)
            )
    }
}

extension Button {
    func roundedAccentButton(width: CGFloat, height: CGFloat) -> some View {
        modifier(RoundedAccentButton(width: width, height: height))
    }
}
