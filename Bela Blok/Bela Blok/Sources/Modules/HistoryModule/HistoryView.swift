import SwiftUI

struct HistoryView: View {
    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: HistoryViewModel
    
    init(viewModel: HistoryViewModel) {
        self._viewModel = .init(wrappedValue: viewModel)
    }
    
    var body: some View {
        ZStack {
            if appState.powerSavingMode {
                Color.black
                    .ignoresSafeArea()
            } else {
                Color(.defaultBackground)
                    .ignoresSafeArea()
            }

            VStack {
                ScrollViewReader { scrollReader in
                    List {
                        ForEach(viewModel.history, id: \.id) { games in
                            ResultRow(
                                numberOfGame: viewModel.getOrderedNumberOfGame(games),
                                weScore: games.weTotalAccumulated,
                                youScore: games.youTotalAccumulated
                            )
                            .showChevron()
                            .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                Button(role: .destructive) {
                                    viewModel.delete(games)
                                } label: {
                                    Image(systemName: "trash")
                                }
                            }
                            .background {
                                NavigationLink("") {
                                    StatsView(viewModel: StatsViewModel(game: games))
                                }
                                .opacity(0)
                            }
                        }
                        
                        Rectangle()
                            .frame(height: 2, alignment: .center)
                            .listRowBackground(Color.clear)
                            .listRowSeparator(.hidden)
                            .id(Constants.BOTTOM_SCROLL_ID)
                    }
                    .scrollContentBackground(.hidden)
                    .onAppear {
                        scrollReader.scrollTo(Constants.BOTTOM_SCROLL_ID, anchor: .bottom)
                    }
                }

                ResultRow(
                    weScore: viewModel.history.filter { $0.weTotalAccumulated > $0.youTotalAccumulated }.count,
                    youScore: viewModel.history.filter { $0.weTotalAccumulated < $0.youTotalAccumulated }.count
                )
                .padding(.vertical)
                .bold()
            }
        }
        .navigationTitle("Povijest")
        .navigationBarTitleDisplayMode(.large)
    }
}

struct HistoryView_Previews: PreviewProvider {
    static var previews: some View {
        let viewModel = HistoryViewModel()
        viewModel.history = [[Game(),Game()],[Game()]]
        return HistoryView(viewModel: viewModel)
    }
}
