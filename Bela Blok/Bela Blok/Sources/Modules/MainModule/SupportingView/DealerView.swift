import SwiftUI

struct DealerView: View {
    @Binding private var dealer: Dealer
    
    init(dealer: Binding<Dealer>) {
        self._dealer = dealer
    }
    
    var body: some View {
        ZStack {
            Color.accentColor.opacity(0.2).ignoresSafeArea(.all)

            GeometryReader { geo in
                VStack {
                    HStack {
                        Text("Dijeli: \(dealer.description)")
                            .foregroundColor(.primary)
                            .font(.system(size: geo.size.height/15))
                            .padding(.horizontal)
                        Spacer()
                    }
                    
                    Group {
                        HStack {
                            Image(systemName: dealer == .partner ? "person.fill" : "person")
                                .resizable()
                                .scaledToFit()
                                .foregroundColor(.accentColor)
                                .frame(width: geo.size.width/7)
                                .padding()
                                .onTapGesture { dealer = .partner }
                        }
                        
                        HStack {
                            Image(systemName: dealer == .leftOpponent ? "person.fill" : "person")
                                .resizable()
                                .scaledToFit()
                                .foregroundColor(.accentColor)
                                .frame(width: geo.size.width/7)
                                .padding()
                                .onTapGesture { dealer = .leftOpponent }
                            
                            RoundedRectangle(cornerSize: CGSize(width: 8, height: 8))
                                .fill(Color.accentColor.opacity(0.8))
                                .frame(width: geo.size.width/4, height: geo.size.width/4, alignment: .center)
                                .padding(4)
                            Image(systemName: dealer == .rightOpponent ? "person.fill" : "person")
                                .resizable()
                                .scaledToFit()
                                .foregroundColor(.accentColor)
                                .frame(width: geo.size.width/7)
                                .padding()
                                .onTapGesture { dealer = .rightOpponent }
                        }

                        HStack {
                            Image(systemName: dealer == .me ? "person.fill" : "person")
                                .resizable()
                                .scaledToFit()
                                .foregroundColor(.accentColor)
                                .frame(width: geo.size.width/7)
                                .padding()
                                .onTapGesture { dealer = .me }
                        }
                    }
                    .animation(.easeInOut, value: dealer)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
    }
}

struct DealerAlertView_Previews: PreviewProvider {
    static var previews: some View {
        DealerView(dealer: .constant(.me))
    }
}
