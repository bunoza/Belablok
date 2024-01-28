import Combine
import Foundation
import SwiftUI

class InputViewModel: ObservableObject {
    @State private var appState = AppState.shared
    @Binding var currentGame: Game?
    @Published var currentGameEdit: Game = .init()
    var isEditing: Bool

    init(editGame: Binding<Game?> = .constant(.init()), isEditing: Bool = false) {
        _currentGame = editGame
        self.isEditing = isEditing
        if isEditing, let editGame = editGame.wrappedValue {
            currentGameEdit = editGame
        } else {
            currentGameEdit = .init()
        }
        currentGameEdit.didFallIndicator = false
    }

    private func stigljaPressed(for caller: Caller) {
        switch caller {
        case .we:
            if currentGameEdit.weStiglja == 0 {
                currentGameEdit.weStiglja = 1
                currentGameEdit.youStiglja = 0
            } else {
                currentGameEdit.weStiglja = 0
            }
            currentGameEdit.weBaseScore = 162
        case .you:
            if currentGameEdit.youStiglja == 0 {
                currentGameEdit.youStiglja = 1
                currentGameEdit.weStiglja = 0
            } else {
                currentGameEdit.youStiglja = 0
            }
            currentGameEdit.youBaseScore = 162
        }
    }

    func onChangeOfWeScore() {
        currentGameEdit.youBaseScore = 162 - currentGameEdit.weBaseScore
    }

    func onChangeOfYouScore() {
        currentGameEdit.weBaseScore = 162 - currentGameEdit.youBaseScore
    }
    
    func resetCalls() {
        currentGameEdit.resetCalls()
    }
    

    func handleWeCallUpdate(amount: Int) {
        if currentGameEdit.weCallsSum < appState.gameEndScore.amount {
            switch amount {
            case 20:
                if currentGameEdit.weCall20 < 7 { currentGameEdit.weCall20 += 1 }
            case 50:
                if currentGameEdit.weCall50 < 6 { currentGameEdit.weCall50 += 1 }
            case 100:
                if currentGameEdit.weCall100 < 5 { currentGameEdit.weCall100 += 1 }
            case 1001:
                if currentGameEdit.weCallBelot == 0 {
                    currentGameEdit.resetWeCalls()
                    currentGameEdit.weCallBelot += 1
                }
            case 90:
                stigljaPressed(for: .we)
            default:
                break
            }
        }
    }

    func handleYouCallUpdate(amount: Int) {
        if currentGameEdit.youCallsSum < appState.gameEndScore.amount {
            switch amount {
            case 20:
                if currentGameEdit.youCall20 < 7 { currentGameEdit.youCall20 += 1 }
            case 50:
                if currentGameEdit.youCall50 < 6 { currentGameEdit.youCall50 += 1 }
            case 100:
                if currentGameEdit.youCall100 < 5 { currentGameEdit.youCall100 += 1 }
            case 1001:
                if currentGameEdit.youCallBelot == 0 {
                    currentGameEdit.resetYouCalls()
                    currentGameEdit.youCallBelot += 1
                }
            case 90:
                stigljaPressed(for: .you)
            default:
                break
            }
        }
    }

    func saveCurrentGame() {
        if isEditing {
            currentGame = currentGameEdit
        } else {
            currentGameEdit.dealer = appState.currentDealer
            appState.currentGame.append(currentGameEdit)
        }
    }
}
