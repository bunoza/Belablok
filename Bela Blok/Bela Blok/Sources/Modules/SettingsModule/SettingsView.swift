import SwiftUI

struct SettingsView: View {
    @Environment(\.dismiss) private var dismiss
    @Environment(\.presentations) private var presentations

    @StateObject private var appState = AppState.shared
    @State private var showSingleDestructiveAlert: Bool = false
    @State private var showContinueOnNewDevice: Bool = false
    @State private var showDestructiveAlert: Bool = false

    var body: some View {
        NavigationView {
            List {
                Section {
                    Toggle(isOn: $appState.isIdleTimerDisabled) {
                        Text("Ne gasi zaslon")
                    }
                } footer: {
                    Text("Može dovesti do povećanja potrošnje baterije.")
                }

                HStack {
                    Text("Igra se do: ")
                    Picker("", selection: $appState.gameEndScore) {
                        ForEach(PossibleGameEndScore.allCases, id: \.self) { score in
                            Text("\(score.amount)")
                        }
                    }
                    .pickerStyle(.segmented)
                }

                Section {
                    Button {
                        showContinueOnNewDevice = true
                    } label: {
                        Text("Nastavi na drugom uređaju")
                    }
                }

                Section {
                    Button(role: .destructive) {
                        showSingleDestructiveAlert = true
                    } label: {
                        Text("Obriši trenutnu partiju")
                    }

                    Button(role: .destructive) {
                        showDestructiveAlert = true
                    } label: {
                        Text("Obriši povijest igre")
                    }
                }
            }
            .padding(.top)
            .navigationTitle("Postavke")
            .navigationBarTitleDisplayMode(.large)
            .navigationBarBackButtonHidden()
            .sheet(isPresented: $showContinueOnNewDevice) {
                ContinueGameView()
                    .environment(\.presentations, presentations + [$showContinueOnNewDevice])
//                    .presentationDetents([.medium])
            }
            .alert("Jeste li sigurni?", isPresented: $showSingleDestructiveAlert, actions: {
                Button(role: .cancel) {} label: {
                    Text("Odustani")
                }

                Button(role: .destructive) {
                    appState.currentGame = []
                } label: {
                    Text("Obriši")
                }
            }, message: {
                Text("Ova radnja se ne može poništiti.")
            })
            .alert("Jeste li sigurni?", isPresented: $showDestructiveAlert, actions: {
                Button(role: .cancel) {} label: {
                    Text("Odustani")
                }

                Button(role: .destructive) {
                    appState.finishedGames = []
                } label: {
                    Text("Obriši")
                }
            }, message: {
                Text("Ova radnja se ne može poništiti.")
            })
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
