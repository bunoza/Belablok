import SwiftUI

struct HistoryView: View {
    @Environment(\.dismiss) private var dismiss

    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: HistoryViewModel

    init(viewModel: HistoryViewModel) {
        _viewModel = .init(wrappedValue: viewModel)
    }

    var body: some View {
        ZStack {
            Color(.defaultBackground)
                .ignoresSafeArea()

            VStack {
                ScrollViewReader { _ in
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
                                    if viewModel.history.isEmpty {
                                        dismiss()
                                    }
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
                    }
                    .scrollContentBackground(.hidden)
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
        viewModel.history = [[Game(), Game()], [Game()]]
        return HistoryView(viewModel: viewModel)
    }
}
