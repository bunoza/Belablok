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
            .background {
                RoundedRectangle(cornerRadius: 7)
                    .fill(Color("ButtonBackground"))
                    .frame(width: width, height: height)
            }
            .padding()
    }
}

extension Button {
    func roundedAccentButton(width: CGFloat, height: CGFloat) -> some View {
        modifier(RoundedAccentButton(width: width, height: height))
    }
}
