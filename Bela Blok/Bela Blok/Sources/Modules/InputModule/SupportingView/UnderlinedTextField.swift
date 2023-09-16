import Combine
import SwiftUI

struct UnderlinedTextField: View {
    @Binding private var text: String
    
    init(text: Binding<String>) {
        self._text = text
    }
    
    var body: some View {
        TextField("", text: $text)
            .onReceive(Just(text)) { newValue in
                let filtered = newValue.filter { "0123456789".contains($0) }
                if filtered != newValue {
                    self.text = filtered
                }
            }
            .keyboardType(.numberPad)
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
        UnderlinedTextField(text: .constant("Hello, World!"))
    }
}
