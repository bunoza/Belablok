import Charts
import SwiftUI

struct StatsView: View {
    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: StatsViewModel
    @State private var showGraph: Bool = true

    @State private var isExpanded1 = true
    @State private var isExpanded2 = true
    @State private var isExpanded3 = true

    private func statsRow(label: String, weValue: Int, youValue: Int) -> some View {
        HStack {
            Spacer()
            VStack {
                Text(label)
                    .frame(alignment: .center)
                    .multilineTextAlignment(.center)
                Text("\(weValue)")
            }
            Spacer()
            VStack {
                Text(label)
                    .frame(alignment: .center)
                    .multilineTextAlignment(.center)
                Text("\(youValue)")
            }
            Spacer()
        }
    }

    private var title: some View {
        ResultRow(weLabel: "MI", youLabel: "VI")
            .bold()
    }

    @ViewBuilder
    private var statsWithTitle: some View {
        title
        stats
    }

    @ViewBuilder
    private var listWithTitle: some View {
        title
        list
    }

    private var stats: some View {
        VStack(spacing: 24) {
            statsRow(label: "Ukupno bodova:", weValue: viewModel.weTotal, youValue: viewModel.youTotal).bold()

            statsRow(label: "Broj zvanja:", weValue: viewModel.weNumberOfCalls, youValue: viewModel.youNumberOfCalls)

            statsRow(label: "Bodovi iz zvanja:", weValue: viewModel.game.weCallsSum, youValue: viewModel.game.youCallsSum)

            statsRow(label: "Broj padova:", weValue: viewModel.game.weFallCount, youValue: viewModel.game.youFallCount)

            statsRow(label: "Broj štiglji:", weValue: viewModel.game.weStigljaCount, youValue: viewModel.game.youStigljaCount)

            statsRow(label: "Bodovi iz igre:", weValue: viewModel.game.weBaseGame, youValue: viewModel.game.youBaseGame)
        }
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
    }

    private var list: some View {
        VStack(spacing: 10) {
            ForEach(viewModel.game) { game in
                ResultRow(
                    numberOfGame: viewModel.getOrderedNumberOfGame(game),
                    weScore: game.handleSpecialCases.weTotal,
                    youScore: game.handleSpecialCases.youTotal,
                    showFallIcon: game.handleSpecialCases.didFallIndicator,
                    showStigljaIcon: game.handleSpecialCases.isStigljaActive
                )
            }

            Rectangle()
                .frame(height: 2, alignment: .center)
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
                .padding(.vertical, 4)

            ResultRow(
                weScore: viewModel.game.forDisplay.weTotalAccumulated,
                youScore: viewModel.game.forDisplay.youTotalAccumulated,
                shouldShowDiff: appState.shouldShowScoreDifferenceOnHistory
            )
            .padding(.bottom)
        }
    }

    private func customDisclosureGroup<Content: View>(
        isExpanded: Binding<Bool>,
        label: String,
        @ViewBuilder content: @escaping () -> Content
    ) -> some View {
        Section {
            DisclosureGroup(isExpanded: isExpanded) {
                content()
            } label: {
                Text(label)
                    .font(.title)
                    .bold()
            }
            .padding(.bottom)
        }
    }

    @ViewBuilder
    private var statsContent: some View {
        Group {
            customDisclosureGroup(isExpanded: $isExpanded1, label: "Detalji") {
                statsWithTitle
            }

            customDisclosureGroup(isExpanded: $isExpanded2, label: "Graf") {
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

            customDisclosureGroup(isExpanded: $isExpanded3, label: "Tijek") {
                listWithTitle
            }
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
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
            .listStyle(.plain)
        }
        .task {
            await viewModel.onAppear(statsWithTitle, graph, listWithTitle)
        }
        .navigationTitle("Statistika")
        .navigationBarTitleDisplayMode(.large)
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Menu {
                    if let imageToShare = viewModel.stats {
                        ShareLink(item: imageToShare, preview: SharePreview("Detalji", image: imageToShare)) {
                            Label("Podijeli detalje", systemImage: "doc.plaintext")
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

                    if let imageToShare = viewModel.list {
                        ShareLink(item: imageToShare, preview: SharePreview("Tijek", image: imageToShare)) {
                            Label("Podijeli tijek", systemImage: "text.justify")
                        }
                    } else {
                        HStack {
                            ProgressView()
                            Text("Loading")
                        }
                    }

                    if let sharingStats = viewModel.stats,
                       let sharingGraph = viewModel.graph,
                       let sharingList = viewModel.list
                    {
                        ShareLink(items: [sharingStats, sharingGraph, sharingList]) {
                            SharePreview("Detalji, graf, tijek", image: $0)
                        }
                        label: {
                            Label("Podijeli sve", systemImage: "doc.richtext")
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
