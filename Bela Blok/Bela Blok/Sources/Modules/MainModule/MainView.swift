import SwiftUI

struct MainView: View {
    @StateObject private var viewModel: MainViewModel
    @State private var showInputSheet: Bool
    @State private var showDealerSheet: Bool
    @State private var showGameFinishedAlert: Bool
    
    init() {
        _viewModel = .init(wrappedValue: MainViewModel())
        showInputSheet = false
        showDealerSheet = false
        showGameFinishedAlert = false
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea(.all)
                
                VStack {
                    ResultRow(weLabel: "MI", youLabel: "VI")
                        .bold()
                        .padding(.bottom)
                        .padding(.bottom)
                    
                    ScrollViewReader { reader in
                        ScrollView {
                            ForEach(viewModel.currentSession, id: \.id) { game in
                                ResultRow(weScore: game.weTotal, youScore: game.youTotal)
                                    .padding(.bottom, 2)
                                if viewModel.currentSession.count > 1 {
                                    Divider()
                                        .padding(.horizontal, 48)
                                }
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
            }
            .onChange(of: viewModel.currentSession.count) { [count = viewModel.currentSession.count] newCount in
                if newCount > count {
                    if !viewModel.shouldStartNewGame { viewModel.updateDealer() }
                }
            }
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
                onDismiss: { viewModel.updateState() },
                content: {
                    InputView(viewModel: InputViewModel())
                        .interactiveDismissDisabled(true)
                }
            )
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    NavigationLink {
                        HistoryView(viewModel: HistoryViewModel())
                    } label: {
                        Image(systemName: "clock.arrow.circlepath")
                            .foregroundColor(.primary)
                    }
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
