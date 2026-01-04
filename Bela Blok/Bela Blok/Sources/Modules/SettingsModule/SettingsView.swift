import SwiftUI

struct SettingsView: View {
    @Environment(\.dismiss) private var dismiss
    @Environment(\.presentations) private var presentations

    @StateObject private var appState = AppState.shared
    @State private var showSingleDestructiveAlert: Bool = false
    @State private var showContinueOnNewDevice: Bool = false
    @State private var showDestructiveAlert: Bool = false

    @State private var showSingleDestructiveCheckmark: Bool = false
    @State private var showDestructiveCheckmark: Bool = false

    var body: some View {
        NavigationStack {
            List {
                Section {
                    Toggle(isOn: $appState.isIdleTimerDisabled) {
                        Text("Ne gasi zaslon")
                    }
                } footer: {
                    Text("Potrebno je u potpunosti ugasiti i upaliti aplikaciju kako bi se promjena primijenila.")
                }

                Section {
                    Toggle(isOn: $appState.shouldShowScoreDifferenceOnMain) {
                        Text("Prikaži razliku na glavnom ekranu")
                    }
                    Toggle(isOn: $appState.shouldShowScoreDifferenceOnHistory) {
                        Text("Prikaži razliku u povijesti")
                    }
                } header: {
                    Text("Prikaži razliku")
                } footer: {
                    Text("Ako je ovo uključeno, između zbrojeva bodova pokazat će se razlika.")
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
                    NavigationLink {
                        CallSelectionView()
                    } label: {
                        Text("Odaberi tipke za zvanja")
                    }
                } footer: {
                    Text("Odaberi zvanja koja se pojavljuju na zaslonu za unos.")
                }

                Section {
                    NavigationLink {
                        DefaultCallerSelectionView()
                    } label: {
                        Text("Odaberi zadanog pozivatelja aduta")
                    }
                } footer: {
                    Text("Odaberi tko će biti označen kao pozivatelj aduta prilikom novog upisa.")
                }

                Section {
                    Button {
                        showContinueOnNewDevice = true
                    } label: {
                        Text("Nastavi na drugom uređaju")
                    }
                }

                Section {
                    HStack {
                        Button(role: .destructive) {
                            showSingleDestructiveAlert = true
                        } label: {
                            Text("Obriši trenutnu partiju")
                        }
                        Spacer()
                        Image(systemName: "checkmark.circle.fill")
                            .foregroundStyle(Color.blue)
                            .opacity(showSingleDestructiveCheckmark ? 1 : 0)
                            .animation(.bouncy, value: showSingleDestructiveCheckmark)
                    }

                    Button(role: .destructive) {
                        showDestructiveAlert = true
                    } label: {
                        HStack {
                            Text("Obriši povijest igre")
                            Spacer()
                            Image(systemName: "checkmark.circle.fill")
                                .foregroundStyle(Color.blue)
                                .opacity(showDestructiveCheckmark ? 1 : 0)
                                .animation(.bouncy, value: showDestructiveCheckmark)
                        }
                    }
                }
            }
            .navigationTitle("Postavke")
            .navigationBarTitleDisplayMode(.inline)
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
                    showSingleDestructiveCheckmark = true
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
                    showDestructiveCheckmark = true
                } label: {
                    Text("Obriši")
                }
            }, message: {
                Text("Ova radnja se ne može poništiti.")
            })
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button(role: .cancel) {
                        dismiss()
                    }
                }
            }
        }
    }
}

#Preview {
    SettingsView()
}
