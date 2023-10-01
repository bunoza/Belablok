import Combine
import Foundation

class InputViewModel: ObservableObject {
    @Published var currentGame: Game
    
    init(editGame: Game = .init()) {
        self.currentGame = editGame
    }
    
    private func transferCalls(to: Caller) {
        switch to {
        case .we:
            currentGame.weCall20 += currentGame.youCall20
            currentGame.weCall50 += currentGame.youCall50
            currentGame.weCall100 += currentGame.youCall100
            currentGame.weCallBelot += currentGame.youCallBelot
            
            currentGame.youCall20 = 0
            currentGame.youCall50 = 0
            currentGame.youCall100 = 0
            currentGame.youCallBelot = 0
        case .you:
            currentGame.youCall20 += currentGame.weCall20
            currentGame.youCall50 += currentGame.weCall50
            currentGame.youCall100 += currentGame.weCall100
            currentGame.youCallBelot += currentGame.weCallBelot
            
            currentGame.weCall20 = 0
            currentGame.weCall50 = 0
            currentGame.weCall100 = 0
            currentGame.weCallBelot = 0
        }
    }
    
    private func handleFall() {
        switch currentGame.caller {
        case .we:
            if currentGame.weTotal <= currentGame.youTotal {
                currentGame.youBaseScore = 162
                currentGame.weBaseScore = 0
                transferCalls(to: .you)
            }
        case .you:
            if currentGame.youTotal <= currentGame.weTotal {
                currentGame.weBaseScore = 162
                currentGame.youBaseScore = 0
                transferCalls(to: .we)
            }
        }
    }

    func onChangeOfWeScore() {
        currentGame.youBaseScore = 162 - currentGame.weBaseScore
    }

    func onChangeOfYouScore() {
        currentGame.weBaseScore = 162 - currentGame.youBaseScore
    }

    func handleWeCallUpdate(amount: Int) {
        if currentGame.weCallsSum < 1001 {
            switch amount {
            case 20:
                if currentGame.weCall20 < 7 { currentGame.weCall20 += 1 }
            case 50:
                if currentGame.weCall50 < 6 { currentGame.weCall50 += 1 }
            case 100:
                if currentGame.weCall100 < 4 { currentGame.weCall100 += 1 }
            case 1001:
                if currentGame.weCallBelot == 0 { currentGame.weCallBelot += 1 }
            default:
                break
            }
        }
    }
    
    func handleYouCallUpdate(amount: Int) {
        if currentGame.youCallsSum < 1001 {
            switch amount {
            case 20:
                if currentGame.youCall20 < 7 { currentGame.youCall20 += 1 }
            case 50:
                if currentGame.youCall50 < 6 { currentGame.youCall50 += 1 }
            case 100:
                if currentGame.youCall100 < 4 { currentGame.youCall100 += 1 }
            case 1001:
                if currentGame.youCallBelot == 0 { currentGame.youCallBelot += 1 }
            default:
                break
            }
        }
    }

    func saveCurrentGame() {
        handleFall()
        
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
