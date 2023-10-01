import SwiftUI

struct MainView: View {
    @StateObject private var viewModel: MainViewModel
    @State private var showInputSheet: Bool
    @State private var showDealerSheet: Bool
        
    init() {
        _viewModel = .init(wrappedValue: MainViewModel())
        showInputSheet = false
        showDealerSheet = false
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
            .onAppear { viewModel.onAppear() }
            .sheet(
                isPresented: $showDealerSheet,
                content: {
                    DealerView(dealer: $viewModel.dealer)
                        .presentationDetents([.medium])
                }
            )
            .sheet(
                isPresented: $showInputSheet,
                onDismiss: { viewModel.onAppear() },
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
                            //TODO: Implement counter
                        } label: {
                            Text("Dijeli: \(viewModel.dealer.description)")
                                .font(.body)
                                .foregroundColor(.primary)
                        }
                        Spacer()
                        Button {
                            showInputSheet = true
                        } label: {
                            Image(systemName: "square.and.pencil")
                                .imageScale(.large)
                                .foregroundColor(.primary)
                        }
                    }
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
