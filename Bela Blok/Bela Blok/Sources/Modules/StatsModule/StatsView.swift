import Charts
import SwiftUI

struct StatsView: View {
    @Environment(\.colorScheme) var colorScheme
    
    @StateObject private var viewModel: StatsViewModel
    @State private var showGraph: Bool = true
    
    init(viewModel: StatsViewModel) {
        self._viewModel = .init(wrappedValue: viewModel)
    }
    
    var body: some View {
        ZStack {
            Color.green.opacity(0.7).ignoresSafeArea(.all)
            
            List {
                VStack {
                    //MARK: MI - VI
                    ResultRow(weLabel: "MI", youLabel: "VI")
                        .bold()
                        .padding(.vertical)
                    
                    //MARK: Ukupno bodova
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
                    
                    //MARK: Broj zvanja
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
                    
                    //MARK: Bodovi iz zvanja
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
                    
                    //MARK: Bodovi iz igre
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
                    
                    //MARK: Graf
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
                            
                            ForEach(viewModel.weGraphData, id: \.self) { data in
                                LineMark(
                                    x: .value("Redni broj", data.orderedNumber),
                                    y: .value("Bodovi", data.amount),
                                    series: .value("Tim", "Mi")
                                )
                            }
                            .foregroundStyle(by: .value("Tim", "Mi"))
                            
                            ForEach(viewModel.youGraphData, id: \.self) { data in
                                LineMark(
                                    x: .value("Redni broj", data.orderedNumber),
                                    y: .value("Bodovi", data.amount),
                                    series: .value("Tim", "Vi")
                                )
                                .foregroundStyle(by: .value("Tim", "Vi"))
                            }
                        }
                        .padding(.top)
                        .frame(height: 250)
                        .chartYAxisLabel("Bodovi", position: .leading, alignment: .center, spacing: 5)
                        .listRowSeparator(.hidden)
                        .padding()
                        .background {
                            RoundedRectangle(cornerRadius: 7)
                                .fill(colorScheme == .dark
                                      ? Color.black.opacity(0.8)
                                      : Color.white.opacity(0.8)
                                )
                        }
                    }
                }
                .listRowBackground(Color.clear)
                .listRowInsets(EdgeInsets())
            }
            .scrollContentBackground(.hidden)
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
