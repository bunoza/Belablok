import SwiftUI

struct RoundedAccentButton: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.title)
            .foregroundColor(.white)
            .background {
                RoundedRectangle(cornerRadius: 7)
                    .fill(Color.accentColor)
                    .frame(width: 100, height: 48)
            }
            .padding()
    }
}

extension Button {
    func roundedAccentButton() -> some View {
        modifier(RoundedAccentButton())
    }
}
