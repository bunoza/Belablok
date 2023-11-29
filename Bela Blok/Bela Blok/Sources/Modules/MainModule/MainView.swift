import SwiftUI

struct MainView: View {
    @StateObject private var viewModel: MainViewModel
    @State private var showInputSheet: Bool
    @State private var showDealerSheet: Bool
    @State private var showGameFinishedAlert: Bool
    @State private var showSettingsSheet: Bool
    
    init() {
        _viewModel = .init(wrappedValue: MainViewModel())
        showInputSheet = false
        showDealerSheet = false
        showSettingsSheet = false
        showGameFinishedAlert = false
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    ResultRow(weLabel: "MI", youLabel: "VI")
                        .bold()
                        .padding(.top)
                    
                    List {
                        ForEach(viewModel.currentSession, id: \.id) { game in
                            ResultRow(weScore: game.weTotal, youScore: game.youTotal)
                                .padding(.bottom, 2)
                                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                    Button {
                                        viewModel.editingGame = game
                                        showInputSheet = true
                                    } label: {
                                        Image(systemName: "rectangle.and.pencil.and.ellipsis")
                                    }
                                    .tint(Color.blue)
                                    
                                }
                                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                    Button {
                                        viewModel.delete(game)
                                    } label: {
                                        Image(systemName: "trash")
                                    }
                                    .tint(Color.red)
                                }
                        }
                    }
                    .animation(.easeInOut, value: viewModel.currentSession)
                    .scrollContentBackground(.hidden)
                    
                    ResultRow(
                        weScore: viewModel.currentSession.weTotalAccumulated,
                        youScore: viewModel.currentSession.youTotalAccumulated
                    )
                    .animation(.easeInOut, value: viewModel.currentSession)
                    .padding(.vertical)
                    .bold()
                }
                
                if viewModel.shouldStartNewGame {
                    LottieView(name: "confetti-animation")
                        .allowsHitTesting(false)
                        .ignoresSafeArea()
                }
            }
            .onChange(of: viewModel.currentSession.count) { [count = viewModel.currentSession.count] newCount in
                if newCount > count {
                    if !viewModel.shouldStartNewGame { viewModel.updateDealer() }
                }
            }
            .sheet(
                isPresented: $showSettingsSheet,
                content: {
                    SettingsView()
                        .presentationDetents([.medium])
                }
            )
            .sheet(
                isPresented: $showDealerSheet,
                onDismiss: { viewModel.setDealer() },
                content: {
                    DealerView(dealer: $viewModel.dealer)
                        .presentationDetents([.medium])
                }
            )
            .sheet(
                isPresented: $showInputSheet,
                onDismiss: {
                    if let editingGame = viewModel.editingGame {
                        viewModel.change(editingGame)
                    }
                    viewModel.updateState()
                    viewModel.editingGame = nil
                },
                content: {
                    if viewModel.editingGame != nil {
                        InputView(viewModel: InputViewModel(
                            editGame: $viewModel.editingGame,
                            isEditing: true
                        ))
                        .interactiveDismissDisabled(true)
                    } else {
                        InputView(viewModel: InputViewModel())
                            .interactiveDismissDisabled(true)
                    }
                }
            )
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button {
                        showSettingsSheet = true
                    } label: {
                        Image(systemName: "gear")
                            .foregroundColor(.primary)
                    }
                }

                ToolbarItem(placement: .topBarTrailing) {
                    NavigationLink {
                        HistoryView(viewModel: HistoryViewModel())
                    } label: {
                        Image(systemName: "clock.arrow.circlepath")
                            .foregroundColor(.primary)
                    }
                    .disabled(AppState.shared.finishedGames.isEmpty)
                }
                
                ToolbarItem(placement: .bottomBar) {
                    HStack {
                        Button {
                            showDealerSheet = true
                        } label: {
                            Text("Dijeli: \(viewModel.dealer.description)")
                                .animation(.easeInOut, value: viewModel.dealer)
                                .font(.body)
                                .foregroundColor(.primary)
                        }
                        Spacer()
                        Button {
                            if viewModel.shouldStartNewGame {
                                showGameFinishedAlert = true
                            } else {
                                showInputSheet = true
                            }
                        } label: {
                            Image(systemName: "square.and.pencil")
                                .imageScale(.large)
                                .foregroundColor(.primary)
                        }
                    }
                }
            }
            .alert("Nova igra?", isPresented: $showGameFinishedAlert) {
                Button(role: .cancel) {
                    viewModel.startNewGame()
                } label: {
                    Text("Nastavi")
                }
                
                Button(role: .destructive) {
                    showGameFinishedAlert = false
                } label: {
                    Text("Odustani")
                }
            }
            .navigationTitle("Bela Blok")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
