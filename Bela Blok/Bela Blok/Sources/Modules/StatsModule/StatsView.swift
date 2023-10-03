import Charts
import SwiftUI

struct StatsView: View {
    @StateObject private var viewModel: StatsViewModel
    @State private var showGraph: Bool = true
    
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
                
                HStack {
                    Spacer()
                    VStack {
                        Text("Ukupno bodova:")
                        Text("\(viewModel.weTotal)")
                    }
                    Spacer()
                    VStack {
                        Text("Ukupno bodova:")
                        Text("\(viewModel.youTotal)")
                    }
                    Spacer()
                }
                .padding(.vertical)
                
                HStack {
                    Spacer()
                    VStack {
                        Text("Broj zvanja:")
                        Text("\(viewModel.weNumberOfCalls)")
                    }
                    Spacer()
                    VStack {
                        Text("Broj zvanja:")
                        Text("\(viewModel.youNumberOfCalls)")
                    }
                    Spacer()
                }
                .padding(.vertical)
                
                HStack {
                    Spacer()
                    VStack {
                        Text("Bodovi iz zvanja:")
                        Text("\(viewModel.game.weCallsSum)")
                    }
                    Spacer()
                    VStack {
                        Text("Bodovi iz zvanja:")
                        Text("\(viewModel.game.youCallsSum)")
                    }
                    Spacer()
                }
                .padding(.vertical)
                
                HStack {
                    Spacer()
                    VStack {
                        Text("Bodovi iz igre:")
                        Text("\(viewModel.game.weBaseGame)")
                    }
                    Spacer()
                    VStack {
                        Text("Bodovi iz igre:")
                        Text("\(viewModel.game.youBaseGame)")
                    }
                    Spacer()
                }
                .padding(.vertical)
                
//                HStack {
//                    Spacer()
//                    Button {
//                        showGraph.toggle()
//                    } label: {
//                        Text("Prikaži graf")
//                    }
//                    Spacer()
//                }
//                .padding(.vertical)
                
                if showGraph {
                    Chart {
                        LineMark(
                            x: .value("Redni broj", 0),
                            y: .value("Bodovi", 0),
                            series: .value("Tim", "Mi")
                        )
                        .foregroundStyle(by: .value("Tim", "Mi"))
                        
                        LineMark(
                            x: .value("Redni broj", 0),
                            y: .value("Bodovi", 0),
                            series: .value("Tim", "Vi")
                        )
                        .foregroundStyle(by: .value("Tim", "Vi"))
                        
                        ForEach(viewModel.game, id: \.self) { match in
                            LineMark(
                                x: .value("Redni broj", (viewModel.game.firstIndex(of: match) ?? 0) + 1),
                                y: .value("Bodovi", match.weTotal),
                                series: .value("Tim", "Mi")
                            )
                            .foregroundStyle(by: .value("Tim", "Mi"))
                            LineMark(
                                x: .value("Redni broj", (viewModel.game.firstIndex(of: match) ?? 0) + 1),
                                y: .value("Bodovi", match.youTotal),
                                series: .value("Tim", "Vi")
                            )
                            .foregroundStyle(by: .value("Tim", "Vi"))
                        }
                    }
                    .padding()
                    .padding()
                }
            }
        }
        .navigationTitle("Statistika")
        .navigationBarTitleDisplayMode(.large)
    }
}

#Preview {
    StatsView(
        viewModel: StatsViewModel(
            game: [
                Game(
                    id: UUID(),
                    weCall20: 1,
                    weCall50: 1,
                    weCall100: 2,
                    weCallBelot: 0,
                    youCall20: 0,
                    youCall50: 0,
                    youCall100: 0,
                    youCallBelot: 0,
                    caller: .we,
                    weBaseScore: 150,
                    youBaseScore: 12
                ),
                Game(
                    id: UUID(),
                    weCall20: 1,
                    weCall50: 1,
                    weCall100: 2,
                    weCallBelot: 0,
                    youCall20: 0,
                    youCall50: 0,
                    youCall100: 0,
                    youCallBelot: 0,
                    caller: .we,
                    weBaseScore: 150,
                    youBaseScore: 12
                ),
                Game(
                    id: UUID(),
                    weCall20: 0,
                    weCall50: 0,
                    weCall100: 0,
                    weCallBelot: 0,
                    youCall20: 0,
                    youCall50: 1,
                    youCall100: 0,
                    youCallBelot: 0,
                    caller: .you,
                    weBaseScore: 20,
                    youBaseScore: 142
                ),
                Game(
                    id: UUID(),
                    weCall20: 0,
                    weCall50: 0,
                    weCall100: 0,
                    weCallBelot: 1,
                    youCall20: 0,
                    youCall50: 0,
                    youCall100: 0,
                    youCallBelot: 0,
                    caller: .we,
                    weBaseScore: 20,
                    youBaseScore: 142
                ),
            ]
        )
    )
}
