import SwiftUI

struct ResultRow: View {
    private var numberOfGame: Int?
    private let weScore: String
    private let youScore: String
    
    init(numberOfGame: Int? = nil, weScore: Int,youScore: Int) {
        self.numberOfGame = numberOfGame
        self.weScore = String(weScore)
        self.youScore = String(youScore)
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
            .padding(.leading, numberOfGame != nil ? 8 : 0)
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
