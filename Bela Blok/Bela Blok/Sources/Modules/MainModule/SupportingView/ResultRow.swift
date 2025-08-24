import SwiftUI

struct ResultRow: View {
    @State private var showPadTooltip: Bool = false
    @State private var showStigljaTooltip: Bool = false

    private var numberOfGame: Int?
    private let weScore: String
    private let youScore: String
    private var showFallIcon: Bool?
    private var showStigljaIcon: Bool?
    private var shouldShowDiff: Bool
    
    private var weLead: Bool {
        if let weScoreInt = Int(weScore), let youScoreInt = Int(youScore) {
            return weScoreInt > youScoreInt
        }
        return false
    }
    
    private var youLead: Bool {
        if let weScoreInt = Int(weScore), let youScoreInt = Int(youScore) {
            return weScoreInt < youScoreInt
        }
        return false
    }
    
    private var diff: String? {
        if weLead, let weScoreInt = Int(weScore), let youScoreInt = Int(youScore) {
            return String("+\(weScoreInt - youScoreInt)")
        } else if youLead, let weScoreInt = Int(weScore), let youScoreInt = Int(youScore) {
            return String("-\(youScoreInt - weScoreInt)")
        } else {
            return "="
        }
    }

    init(
        numberOfGame: Int? = nil,
        weScore: Int,
        youScore: Int,
        showFallIcon: Bool = false,
        showStigljaIcon: Bool = false,
        shouldShowDiff: Bool = false
    ) {
        self.numberOfGame = numberOfGame
        self.weScore = String(weScore)
        self.youScore = String(youScore)
        self.showFallIcon = showFallIcon
        self.showStigljaIcon = showStigljaIcon
        self.shouldShowDiff = shouldShowDiff
    }

    init(weLabel: String, youLabel: String) {
        weScore = weLabel
        youScore = youLabel
        shouldShowDiff = false
    }

    var body: some View {
        ZStack {
            if let numberOfGame {
                HStack {
                    Text("\(numberOfGame).")
                        .font(.title3)
                        .frame(alignment: .leading)

                    Spacer()

                    Group {
                        if let shouldShow = showStigljaIcon, shouldShow {
                            Button {
                                showStigljaTooltip = true
                            } label: {
                                Image(systemName: "theatermasks.fill")
                                    .font(.title3)
                                    .foregroundStyle(Color.red)
                            }
                        } else if let shouldShow = showFallIcon, shouldShow {
                            Button {
                                showPadTooltip = true
                            } label: {
                                Image(systemName: "figure.fall.circle")
                                    .font(.title2)
                                    .foregroundStyle(Color.red)
                            }
                        }
                    }
                }
            }

            HStack {
                VStack(alignment: .center) {
                    Text(weScore)
                        .font(.largeTitle)
                        .frame(alignment: .center)
                }
                .frame(maxWidth: .infinity)
                
                if shouldShowDiff, let diff {
                    VStack(alignment: .center) {
                        Text(diff)
                            .font(.caption)
                            .frame(alignment: .center)
                    }
                }

                VStack(alignment: .center) {
                    Text(youScore)
                        .font(.largeTitle)
                        .frame(alignment: .center)
                }
                .frame(maxWidth: .infinity)
            }
            .padding(.horizontal)
            .padding(.horizontal)
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }

    func showChevron() -> some View {
        ZStack {
            self
            HStack {
                Spacer()
                Image(systemName: "chevron.right")
            }
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
}

struct ResultRow_Previews: PreviewProvider {
    static var previews: some View {
        List {
            ResultRow(weScore: 36, youScore: 126)
            ResultRow(weScore: 36, youScore: 126)
            ResultRow(weScore: 36, youScore: 126)
                .showChevron()
            ResultRow(weScore: 36, youScore: 126, shouldShowDiff: true)
        }
    }
}
