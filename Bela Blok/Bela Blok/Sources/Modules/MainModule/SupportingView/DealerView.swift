import SwiftUI

struct DealerView: View {
    @StateObject private var appState = AppState.shared
    @Binding private var dealer: Dealer

    init(dealer: Binding<Dealer>) {
        _dealer = dealer
    }

    var body: some View {
        ZStack {
            if appState.powerSavingMode {
                Color.black
                    .ignoresSafeArea()
            } else {
                Color(.defaultBackground)
                    .ignoresSafeArea()
            }

            GeometryReader { geo in
                VStack {
                    HStack {
                        Spacer()
                        Text("Dijeli: \(dealer.description)")
                            .font(.system(size: geo.size.height / 17))
                            .padding(.horizontal)
                        Spacer()
                    }

                    Group {
                        HStack {
                            Button {
                                dealer = .partner
                            } label: {
                                Image(systemName: dealer == .partner ? "person.fill" : "person")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: geo.size.width / 7)
                                    .padding()
                            }
                        }

                        HStack {
                            Button {
                                dealer = .leftOpponent
                            } label: {
                                Image(systemName: dealer == .leftOpponent ? "person.fill" : "person")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: geo.size.width / 7)
                                    .padding()
                            }

                            RoundedRectangle(cornerSize: CGSize(width: 8, height: 8))
                                .frame(width: geo.size.width / 4, height: geo.size.width / 4, alignment: .center)
                                .foregroundStyle(Color.accentColor.opacity(0.8))
                                .padding(4)

                            Button {
                                dealer = .rightOpponent
                            } label: {
                                Image(systemName: dealer == .rightOpponent ? "person.fill" : "person")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: geo.size.width / 7)
                                    .padding()
                            }
                        }

                        HStack {
                            Button {
                                dealer = .me
                            } label: {
                                Image(systemName: dealer == .me ? "person.fill" : "person")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: geo.size.width / 7)
                                    .padding()
                            }
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
