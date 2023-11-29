import SwiftUI

struct HistoryView: View {
    @StateObject private var viewModel: HistoryViewModel
    
    init(viewModel: HistoryViewModel) {
        self._viewModel = .init(wrappedValue: viewModel)
    }
    
    var body: some View {
        ZStack {
            VStack {
                List {
                    ForEach(viewModel.history, id: \.id) { games in
                        ResultRow(weScore: games.weTotalAccumulated, youScore: games.youTotalAccumulated)
                            .showChevron()
                            .background {
                                NavigationLink("") {
                                    StatsView(viewModel: StatsViewModel(game: games))
                                }
                                .opacity(0)
                            }
                    }
                    .onDelete(perform: viewModel.delete)
                }
                .scrollContentBackground(.hidden)
                
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
        .toolbar {
            EditButton()
                .foregroundColor(.primary)
        }
    }
}

struct HistoryView_Previews: PreviewProvider {
    static var previews: some View {
        let viewModel = HistoryViewModel()
        viewModel.history = [[Game(),Game()],[Game()]]
        return HistoryView(viewModel: viewModel)
    }
}
