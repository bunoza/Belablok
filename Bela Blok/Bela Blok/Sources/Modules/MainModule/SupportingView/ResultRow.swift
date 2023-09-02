import SwiftUI

struct ResultRow: View {
    private let weScore: String
    private let youScore: String
    
    init(
        weScore: String,
        youScore: String
    ) {
        self.weScore = weScore
        self.youScore = youScore
    }
    
    var body: some View {
        HStack {
            Spacer()
            Text(weScore)
                .font(.largeTitle)
            Spacer()
            Text(youScore)
                .font(.largeTitle)
            Spacer()
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
}

struct ResultRow_Previews: PreviewProvider {
    static var previews: some View {
        List {
            ResultRow(
                weScore: "36",
                youScore: "126"
            )
            ResultRow(
                weScore: "36",
                youScore: "126"
            )
            ResultRow(
                weScore: "36",
                youScore: "126"
            )
        }
    }
}
