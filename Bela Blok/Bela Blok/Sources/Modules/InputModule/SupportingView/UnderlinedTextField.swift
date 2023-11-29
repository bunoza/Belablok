import Combine
import SwiftUI

struct UnderlinedTextField: View {
    @Binding private var score: Int
    
    @State private var text: String
    
    init(score: Binding<Int>) {
        self._score = score
        text = score.wrappedValue == -1 ? "" : String(score.wrappedValue)
    }
    
    var body: some View {
        TextField("0", text: $text)
            .onChange(of: text) { [text] newState in
                if newState.isEmpty {
                    self.text = "0"
                    self.score = 0
                    return
                }
                
                let filteredString = newState.filter { 
                    Constants.ACCEPTABLE_CHARS_FOR_INPUT.contains($0)
                }
                
                if let validInt = Int(filteredString),
                   validInt >= 0,
                   validInt <= 162 {
                    self.text = String(validInt)
                    self.score = validInt
                } else {
                    self.text = text
                }
            }
            .onChange(of: score) { newState in
                self.text = String(newState)
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
        UnderlinedTextField(score: .constant(25))
    }
}
