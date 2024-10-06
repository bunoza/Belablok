import SwiftUI

struct CallSelectionView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var currentSelection = AppState.shared.selectedCalls

    var body: some View {
        List(Calls.allCases, id: \.self) { call in
            Button {
                if currentSelection.contains(call) {
                    currentSelection.removeAll(where: { call == $0 })
                } else {
                    currentSelection.append(call)
                }
            } label: {
                HStack {
                    Text(call.description)
                    Spacer()
                    if currentSelection.contains(call) {
                        Image(systemName: "checkmark")
                    }
                }
            }
        }
        .navigationTitle("Upravljaj zvanjima")
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button {
                    AppState.shared.selectedCalls = currentSelection
                    dismiss()
                } label: {
                    Text("Spremi")
                }
                .disabled(Set(AppState.shared.selectedCalls) == Set(currentSelection))
            }
        }
        .interactiveDismissDisabled()
    }
}

#Preview {
    CallSelectionView()
}
