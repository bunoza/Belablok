// Urheberrechtshinweis: Diese Software ist urheberrechtlich geschützt. Das Urheberrecht liegt bei
// Research Industrial Systems Engineering (RISE) Forschungs-, Entwicklungs- und Großprojektberatung GmbH,
// soweit nicht im Folgenden näher gekennzeichnet.

import SwiftUI

struct SettingsView: View {
    @Environment(\.dismiss) private var dismiss
    
    @State private var appState = AppState.shared

    var body: some View {
        NavigationStack {
            List {
                Section {
                    Toggle(isOn: $appState.isIdleTimerDisabled, label: {
                        Text("Ne gasi zaslon")
                    })
                } footer: {
                    Text("Može dovesti do povećanja potrošnje baterije.")
                }
            }
            .navigationTitle("Postavke")
            .navigationBarTitleDisplayMode(.large)
            .navigationBarBackButtonHidden()
            .toolbar {
                ToolbarItem(placement: .confirmationAction) {
                    Button {
                        dismiss()
                    } label: {
                        Text("Gotovo")
                    }

                }
            }
        }
    }
}

#Preview {
    SettingsView()
}
