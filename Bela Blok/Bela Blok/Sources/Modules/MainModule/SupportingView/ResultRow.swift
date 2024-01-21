import SwiftUI

struct ResultRow: View {
    private var numberOfGame: Int?
    private let weScore: String
    private let youScore: String
    private var showFallIcon: Bool?

    init(
        numberOfGame: Int? = nil,
        weScore: Int,
        youScore: Int,
        showFallIcon: Bool = false
    ) {
        self.numberOfGame = numberOfGame
        self.weScore = String(weScore)
        self.youScore = String(youScore)
        self.showFallIcon = showFallIcon
    }

    init(weLabel: String, youLabel: String) {
        weScore = weLabel
        youScore = youLabel
    }

    var body: some View {
        ZStack {
            if let numberOfGame {
                HStack {
                    Text("\(numberOfGame).")
                        .font(.title3)
                        .frame(alignment: .leading)

                    Spacer()

                    if let shouldShow = showFallIcon,
                       shouldShow == true
                    {
                        Image(systemName: "figure.fall.circle")
                            .font(.title2)
                            .foregroundStyle(Color.red)
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
        }
    }
}
