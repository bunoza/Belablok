import SwiftUI

struct MainView: View {
    @StateObject private var appState: AppState = .shared
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
                if appState.powerSavingMode {
                    Color.black
                        .ignoresSafeArea()
                } else {
                    Color(.defaultBackground)
                        .ignoresSafeArea()
                }
                VStack {
                    ResultRow(weLabel: "MI", youLabel: "VI")
                        .bold()
                        .padding(.top)
                    
                    ScrollViewReader { scrollReader in
                        List(viewModel.currentSession) { game in
                            ResultRow(
                                numberOfGame: viewModel.getOrderedNumberOfGame(game),
                                weScore: game.weTotal,
                                youScore: game.youTotal
                            )
                                .padding(.bottom, 2)
                                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                    Button(role: .cancel) {
                                        viewModel.editingGame = game
                                        showInputSheet = true
                                    } label: {
                                        Image(systemName: "rectangle.and.pencil.and.ellipsis")
                                    }
                                }
                                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                    Button(role: .destructive) {
                                        viewModel.delete(game)
                                    } label: {
                                        Image(systemName: "trash")
                                    }
                                }
                        }
                    }
                    .animation(.easeInOut, value: viewModel.currentSession)
                    .scrollContentBackground(.hidden)

                    Spacer()
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
                onDismiss: { viewModel.updateState() },
                content: { SettingsView() }
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
                            .rotationEffect(.degrees(showSettingsSheet ? 180 : 0))
                            .animation(.easeInOut, value: showSettingsSheet)
                    }
                }

                ToolbarItem(placement: .topBarTrailing) {
                    NavigationLink {
                        HistoryView(viewModel: HistoryViewModel())
                    } label: {
                        Image(systemName: "clock.arrow.circlepath")
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
