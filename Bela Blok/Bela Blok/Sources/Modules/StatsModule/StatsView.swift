import Charts
import SwiftUI

struct StatsView: View {
    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: StatsViewModel
    @State private var showGraph: Bool = true

    private func statsRow(label: String, weValue: Int, youValue: Int) -> some View {
        HStack {
            Spacer()
            VStack {
                Text(label)
                Text("\(weValue)")
            }
            Spacer()
            VStack {
                Text(label)
                Text("\(youValue)")
            }
            Spacer()
        }
    }

    private var stats: some View {
        VStack(spacing: 24) {
            ResultRow(weLabel: "MI", youLabel: "VI")
                .bold()
                .padding(.vertical)

            statsRow(label: "Ukupno bodova:", weValue: viewModel.weTotal, youValue: viewModel.youTotal).bold()

            statsRow(label: "Broj zvanja:", weValue: viewModel.weNumberOfCalls, youValue: viewModel.youNumberOfCalls)

            statsRow(label: "Bodovi iz zvanja:", weValue: viewModel.game.weCallsSum, youValue: viewModel.game.youCallsSum)

            statsRow(label: "Broj padova:", weValue: viewModel.game.weFallCount, youValue: viewModel.game.youFallCount)

            statsRow(label: "Broj štiglji:", weValue: viewModel.game.weStigljaCount, youValue: viewModel.game.youStigljaCount)

            statsRow(label: "Bodovi iz igre:", weValue: viewModel.game.weBaseGame, youValue: viewModel.game.youBaseGame)
        }
        .listRowBackground(Color.clear)
        .listRowInsets(EdgeInsets())
    }

    private var graph: some View {
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
        .listRowBackground(Color.clear)
        .padding(.top)
        .frame(width: 300, height: 250)
        .chartYAxisLabel("Bodovi", position: .leading, alignment: .center, spacing: 5)
        .listRowSeparator(.hidden)
        .padding()
    }

    private var statsContent: some View {
        Group {
            stats
            if showGraph {
                HStack {
                    Spacer()
                    graph
                    Spacer()
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            }
        }
    }

    init(viewModel: StatsViewModel) {
        _viewModel = .init(wrappedValue: viewModel)
    }

    var body: some View {
        ZStack {
            Color(.defaultBackground)
                .ignoresSafeArea()

            List {
                statsContent
            }
            .scrollContentBackground(.hidden)
        }
        .task {
            await viewModel.onAppear(stats, graph, statsContent)
        }
        .navigationTitle("Statistika")
        .navigationBarTitleDisplayMode(.large)
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Menu {
                    if let imageToShare = viewModel.stats {
                        ShareLink(item: imageToShare, preview: SharePreview("Statistika", image: imageToShare)) {
                            Label("Podijeli statistiku", systemImage: "doc.plaintext")
                        }
                    } else {
                        HStack {
                            ProgressView()
                            Text("Loading")
                        }
                    }

                    if let imageToShare = viewModel.graph {
                        ShareLink(item: imageToShare, preview: SharePreview("Graf", image: imageToShare)) {
                            Label("Podijeli graf", systemImage: "photo")
                        }
                    } else {
                        HStack {
                            ProgressView()
                            Text("Loading")
                        }
                    }

                    if let sharingStats = viewModel.stats,
                       let sharingGraph = viewModel.graph
                    {
                        ShareLink(items: [sharingStats, sharingGraph]) {
                            SharePreview("Statistika i graf", image: $0)
                        }
                        label: {
                            Label("Podijeli oboje", systemImage: "doc.richtext")
                        }
                    } else {
                        HStack {
                            ProgressView()
                            Text("Loading")
                        }
                    }
                } label: {
                    Image(systemName: "square.and.arrow.up")
                }
            }
        }
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
