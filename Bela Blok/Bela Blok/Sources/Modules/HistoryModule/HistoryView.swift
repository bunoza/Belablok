import SwiftUI

struct HistoryView: View {
    @ObservedObject private var viewModel: HistoryViewModel
    
    init(viewModel: HistoryViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        ZStack {
            Color.green.opacity(0.8).ignoresSafeArea(.all)
            
            List {
                ForEach(viewModel.history, id: \.id) { games in
                    ResultRow(weScore: games.weTotalAccumulated, youScore: games.youTotalAccumulated)
                }
                .onDelete(perform: delete)
            }
            .scrollContentBackground(.hidden)
        }
        .onAppear { viewModel.onAppear() }
        .navigationTitle("Povijest")
        .toolbar {
            EditButton()
                .foregroundColor(.primary)
        }
    }
    
    func delete(at offsets: IndexSet) {
        viewModel.history.remove(atOffsets: offsets)
        AppState.shared.finishedGames = viewModel.history
    }
}

struct HistoryView_Previews: PreviewProvider {
    static var previews: some View {
        HistoryView(viewModel: HistoryViewModel())
    }
}
