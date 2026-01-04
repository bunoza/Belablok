import SwiftUI

struct DefaultCallerSelectionView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var currentSelection = AppState.shared.defaultCallerSelection

    var body: some View {
        List(Caller.allCases, id: \.self) { call in
            Button {
                currentSelection = call
            } label: {
                HStack {
                    Text(call.name)
                    Spacer()
                    if currentSelection == call {
                        Image(systemName: "checkmark")
                    }
                }
            }
        }
        .navigationTitle("Upravljaj pozivateljem")
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button(role: .confirm) {
                    AppState.shared.defaultCallerSelection = currentSelection
                    dismiss()
                }
                .disabled(AppState.shared.defaultCallerSelection == currentSelection)
            }
        }
        .interactiveDismissDisabled()
    }
}

#Preview {
    DefaultCallerSelectionView()
}
