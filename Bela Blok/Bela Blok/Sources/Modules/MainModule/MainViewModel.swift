import Foundation

class MainViewModel: ObservableObject {
    @Published var currentSession: [Game] = []
    @Published var dealer: Dealer = .me
    
    var lastGame: Game? {
        guard let lastGame = currentSession.last else { return nil }
        return lastGame
    }
    
    private func updateDealerOnGameFinished() {
        dealer = dealer.nextDealer
        if currentSession.weTotalAccumulated >= 1001,
           currentSession.youTotalAccumulated >= 1001 {
            if currentSession.weTotalAccumulated > currentSession.youTotalAccumulated {
                if ![.me, .partner].contains(dealer) {
                    dealer = dealer.nextDealer
                }
            }
        }
        if currentSession.weTotalAccumulated >= 1001 {
            if ![.me, .partner].contains(dealer) {
                dealer = dealer.nextDealer
            }
        }
        if currentSession.youTotalAccumulated >= 1001 {
            if ![.leftOpponent, .rightOpponent].contains(dealer) {
                dealer = dealer.nextDealer
            }
        }
    }
    
    func onAppear() {
        let appState = AppState.shared
        self.currentSession = appState.currentGame
        self.dealer = appState.dealer

        
        if currentSession.weTotalAccumulated >= 1001 || currentSession.youTotalAccumulated >= 1001 {
            appState.finishedGames.append(currentSession)
            updateDealerOnGameFinished()
            appState.currentGame = []
        }
    }
}
