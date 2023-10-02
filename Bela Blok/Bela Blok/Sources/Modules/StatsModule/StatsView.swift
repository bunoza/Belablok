import SwiftUI

struct StatsView: View {
    @StateObject private var viewModel: StatsViewModel
    
    init(viewModel: StatsViewModel) {
        self._viewModel = .init(wrappedValue: viewModel)
    }

    var body: some View {
        ZStack {
            Color.green.opacity(0.8).ignoresSafeArea(.all)
            
            VStack {
                ResultRow(weLabel: "MI", youLabel: "VI")
                    .bold()
                    .padding(.bottom)
                    .padding(.bottom)
            }
        }
        .navigationTitle("Statistika")
    }
}

#Preview {
    StatsView(viewModel: StatsViewModel(game: [Game(id: UUID(), weCall20: 1, weCall50: 1, weCall100: 0, weCallBelot: 0, youCall20: 0, youCall50: 0, youCall100: 0, youCallBelot: 0, caller: .we, weBaseScore: 150, youBaseScore: 12) , Game()]))
}
