import ActivityKit
import StoreKit
import SwiftUI

struct BelaBlokWidgetAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        var wePoints: Int
        var youPoints: Int
        
        init(wePoints: Int, youPoints: Int) {
            self.wePoints = wePoints
            self.youPoints = youPoints
        }
    }
}

struct MainView: View {
    @Environment(\.presentations) private var presentations
    @Environment(\.requestReview) private var requestReview
    
    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: MainViewModel
    @StateObject private var activityManager: ActivityManager = .shared

    @State private var showInputSheet: Bool = false
    @State private var showDealerSheet: Bool = false
    @State private var showGameFinishedAlert: Bool = false
    @State private var showSettingsSheet: Bool = false
    @State private var showBottomBar: Bool = false
    
    init() {
        _viewModel = .init(wrappedValue: MainViewModel())
    }

    var body: some View {
        NavigationView {
            ZStack {
                Color(.defaultBackground)
                    .ignoresSafeArea()

                if !viewModel.currentSession.isEmpty {
                    VStack {
                        ResultRow(weLabel: "MI", youLabel: "VI")
                            .bold()
                            .padding(.top)
                            .padding(.horizontal)
                            .padding(.horizontal)
                            .padding(.horizontal)

                        List {
                            ForEach(viewModel.currentSession) { game in
                                ResultRow(
                                    numberOfGame: viewModel.getOrderedNumberOfGame(game),
                                    weScore: game.handleSpecialCases.weTotal,
                                    youScore: game.handleSpecialCases.youTotal,
                                    showFallIcon: game.handleSpecialCases.didFallIndicator,
                                    showStigljaIcon: game.handleSpecialCases.isStigljaActive
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
                                        viewModel.dealerStepBack()
                                    } label: {
                                        Image(systemName: "trash")
                                            .resizable()
                                    }
                                }
                            }

                            Rectangle()
                                .frame(height: 2, alignment: .center)
                                .listRowBackground(Color.clear)
                                .listRowSeparator(.hidden)
                        }
                        .animation(.easeInOut, value: viewModel.currentSession)
                        .scrollContentBackground(.hidden)

                        ResultRow(
                            weScore: viewModel.currentSession.forDisplay.weTotalAccumulated,
                            youScore: viewModel.currentSession.forDisplay.youTotalAccumulated
                        )
                        .animation(.easeInOut, value: viewModel.currentSession)
                        .padding(.bottom)
                        .padding(.horizontal)
                        .padding(.horizontal)
                        .padding(.horizontal)
                        .bold()
                    }

                    if viewModel.shouldStartNewGame {
                        LottieView(name: "confetti-animation")
                            .allowsHitTesting(false)
                            .ignoresSafeArea()
                            .onAppear {
                                if appState.reviewCounter == 10 {
                                    appState.reviewCounter = 0
                                }
                                if appState.reviewCounter == 0 {
                                    requestReview()
                                }
                                if viewModel.currentSession.weTotalAccumulated > viewModel.currentSession.youTotalAccumulated {
                                    appState.reviewCounter += 1
                                }
                            }
                    }
                } else {
                    GeometryReader { geo in
                        VStack {
                            Spacer()

                            HStack {
                                Spacer()
                                LottieView(name: "cards-animation", loopMode: .autoReverse)
                                    .frame(width: geo.size.width * 0.9, height: geo.size.height * 0.275)
                                    .allowsHitTesting(false)
                                Spacer()
                            }

                            HStack {
                                Text("Pritisni na ")
                                    .font(.footnote)
                                Image(systemName: "square.and.pencil")
                                    .imageScale(.medium)
                                Text("za početak")
                                    .font(.footnote)
                            }

                            Spacer()
                        }
                    }
                }
            }
            .task { await updateActivity() }
            .animation(.easeInOut, value: viewModel.currentSession)
            .onChange(of: viewModel.currentSession.count) { [oldValue = viewModel.currentSession.count] newValue in
                if newValue > oldValue {
                    if !viewModel.shouldStartNewGame { viewModel.updateDealer() }
                }
                Task {
                    await updateActivity()
                }
            }
            .onAppear {
                showBottomBar = true
            }
            .onDisappear {
                showBottomBar = false
            }
            .sheet(
                isPresented: $showSettingsSheet,
                onDismiss: {
                    viewModel.updateState()
                    Task {
                        await updateActivity()
                    }
                },
                content: {
                    SettingsView()
                        .environment(\.presentations, presentations + [$showSettingsSheet])
                }
            )
            .sheet(
                isPresented: $showDealerSheet,
                onDismiss: {
                    viewModel.setDealer()
                    Task {
                        await updateActivity()
                    }
                },
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
                    Task {
                        await updateActivity()
                    }
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
                    if showBottomBar {
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
    
    private func updateActivity() async {
        if activityManager.activityID?.isEmpty == false {
            await activityManager.updateActivity(
                wePoints: viewModel.currentSession.forDisplay.weTotalAccumulated,
                youPoints: viewModel.currentSession.forDisplay.youTotalAccumulated
            )
        } else {
            await activityManager.start(
                wePoints: viewModel.currentSession.forDisplay.weTotalAccumulated,
                youPoints: viewModel.currentSession.forDisplay.youTotalAccumulated
            )
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
