import SwiftUI

struct UnderlinedTextField: View {
    @State private var text: String
    
    init(text: String) {
        self.text = text
    }
    
    var body: some View {
        TextField("", text: $text)
            .font(.largeTitle)
            .multilineTextAlignment(.center)
            .labelsHidden()
            .background {
                VStack {
                    Spacer()
                    Rectangle()
                        .frame(height: 2.0, alignment: .bottom)
                        .foregroundColor(.primary)
                }
                .padding(.horizontal)
            }
    }
}

struct UnderlinedTextField_Previews: PreviewProvider {
    static var previews: some View {
        UnderlinedTextField(text: "Hello, World!")
    }
}
