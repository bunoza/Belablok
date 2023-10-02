import Foundation

class MainViewModel: ObservableObject {
    @Published var currentSession: [Game] = []
    @Published var dealer: Dealer = .me
    
    init() {
        updateState()
    }
    var lastGame: Game? {
        guard let lastGame = currentSession.last else { return nil }
        return lastGame
    }
    
    var shouldStartNewGame: Bool {
        currentSession.weTotalAccumulated >= 1001 || currentSession.youTotalAccumulated >= 1001
    }
    
    private func updateDealerOnGameFinished() {
        dealer = dealer.nextDealer
        if currentSession.weTotalAccumulated >= 1001,
           currentSession.youTotalAccumulated >= 1001 {
            if currentSession.weTotalAccumulated > currentSession.youTotalAccumulated {
                if ![.me, .partner].contains(dealer) {
                    dealer = dealer.nextDealer
                    setDealer()
                    return
                }
            } else {
                if ![.leftOpponent, .rightOpponent].contains(dealer) {
                    dealer = dealer.nextDealer
                    setDealer()
                    return
                }
            }
        }
        if currentSession.weTotalAccumulated >= 1001 {
            if [.leftOpponent, .rightOpponent].contains(dealer) {
                dealer = dealer.nextDealer
            }
        }
        if currentSession.youTotalAccumulated >= 1001 {
            if [.me, .partner].contains(dealer) {
                dealer = dealer.nextDealer
            }
        }
        setDealer()
    }
    
    func updateDealer() {
        dealer = dealer.nextDealer
        setDealer()
    }
    
    func setDealer() {
        let appState = AppState.shared
        appState.currentDealer = dealer
    }
    
    func startNewGame() {
        let appState = AppState.shared
        appState.finishedGames.append(currentSession)
        appState.currentGame = []
        updateDealerOnGameFinished()
        currentSession = []
    }
    
    func updateState() {
        let appState = AppState.shared
        self.currentSession = appState.currentGame
        self.dealer = appState.currentDealer
    }
}
