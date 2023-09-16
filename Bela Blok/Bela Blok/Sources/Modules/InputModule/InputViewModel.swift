import Combine
import Foundation

class InputViewModel: ObservableObject {
    @Published var currentGame: Game
    
    var cancellables: [AnyCancellable] = []
    
    init(editGame: Game = .init()) {
        self.currentGame = editGame
    }

    func onChangeOfWeScore(weScore: String) {
        if let weScoreInt = weScore.codingKey.intValue {
            currentGame.youScoreString = String(162 - weScoreInt)
        }
    }
    
    func onChangeOfYouScore(youScore: String) {
        if let youScoreInt = youScore.codingKey.intValue {
            currentGame.weScoreString = String(162 - youScoreInt)
        }
    }
    
    func handleWeCallUpdate(amount: Int) {
        if currentGame.getWeCallsSum() < 1001 {
            switch amount {
            case 20:
                currentGame.weCall20 += 1
            case 50:
                currentGame.weCall50 += 1
            case 100:
                currentGame.weCall100 += 1
            case 1001:
                if currentGame.weCallBelot == 0 { currentGame.weCallBelot += 1 }
            default:
                break
            }
        }
    }
    
    func handleYouCallUpdate(amount: Int) {
        if currentGame.getYouCallsSum() < 1001 {
            switch amount {
            case 20:
                currentGame.youCall20 += 1
            case 50:
                currentGame.youCall50 += 1
            case 100:
                currentGame.youCall100 += 1
            case 1001:
                if currentGame.youCallBelot == 0 { currentGame.youCallBelot += 1 }
            default:
                break
            }
        }
    }
    
    func saveCurrentGame() {
        AppState.shared.currentGame.append(currentGame)
    }
    
    func resetCalls() {
        currentGame.weCall20 = 0
        currentGame.weCall50 = 0
        currentGame.weCall100 = 0
        currentGame.weCallBelot = 0
        currentGame.youCall20 = 0
        currentGame.youCall50 = 0
        currentGame.youCall100 = 0
        currentGame.youCallBelot = 0
    }
}
