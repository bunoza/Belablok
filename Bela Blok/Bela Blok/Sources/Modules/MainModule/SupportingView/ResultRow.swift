import SwiftUI

struct ResultRow: View {
    private let weScore: String
    private let youScore: String
    
    init(weScore: Int,youScore: Int) {
        self.weScore = String(weScore)
        self.youScore = String(youScore)
    }
    
    init(weLabel: String, youLabel: String) {
        weScore = weLabel
        youScore = youLabel
    }
    
    var body: some View {
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
        }
    }
}
