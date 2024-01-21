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
    }

    private func transferCalls(to: Caller) {
        switch to {
        case .we:
            currentGameEdit.weCall20 += currentGameEdit.youCall20
            currentGameEdit.weCall50 += currentGameEdit.youCall50
            currentGameEdit.weCall100 += currentGameEdit.youCall100
            currentGameEdit.weCallBelot += currentGameEdit.youCallBelot

            currentGameEdit.youCall20 = 0
            currentGameEdit.youCall50 = 0
            currentGameEdit.youCall100 = 0
            currentGameEdit.youCallBelot = 0
        case .you:
            currentGameEdit.youCall20 += currentGameEdit.weCall20
            currentGameEdit.youCall50 += currentGameEdit.weCall50
            currentGameEdit.youCall100 += currentGameEdit.weCall100
            currentGameEdit.youCallBelot += currentGameEdit.weCallBelot

            currentGameEdit.weCall20 = 0
            currentGameEdit.weCall50 = 0
            currentGameEdit.weCall100 = 0
            currentGameEdit.weCallBelot = 0
        }
    }

    private func handleFall() {
        switch currentGameEdit.caller {
        case .we:
            if currentGameEdit.weTotal <= currentGameEdit.youTotal {
                currentGameEdit.youBaseScore = 162
                currentGameEdit.weBaseScore = 0
                transferCalls(to: .you)
                currentGameEdit.didFallIndicator = true
            }
        case .you:
            if currentGameEdit.youTotal <= currentGameEdit.weTotal {
                currentGameEdit.weBaseScore = 162
                currentGameEdit.youBaseScore = 0
                transferCalls(to: .we)
                currentGameEdit.didFallIndicator = true
            }
        }
    }

    private func resetWeCalls() {
        currentGameEdit.weCall20 = 0
        currentGameEdit.weCall50 = 0
        currentGameEdit.weCall100 = 0
        currentGameEdit.weCallBelot = 0
    }

    private func resetYouCalls() {
        currentGameEdit.youCall20 = 0
        currentGameEdit.youCall50 = 0
        currentGameEdit.youCall100 = 0
        currentGameEdit.youCallBelot = 0
    }

    func resetCalls() {
        resetWeCalls()
        resetYouCalls()
    }

    func onChangeOfWeScore() {
        currentGameEdit.youBaseScore = 162 - currentGameEdit.weBaseScore
    }

    func onChangeOfYouScore() {
        currentGameEdit.weBaseScore = 162 - currentGameEdit.youBaseScore
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
                    resetWeCalls()
                    currentGameEdit.weCallBelot += 1
                }
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
                    resetYouCalls()
                    currentGameEdit.youCallBelot += 1
                }
            default:
                break
            }
        }
    }

    func saveCurrentGame() {
        handleFall()
        if isEditing {
            currentGame = currentGameEdit
        } else {
            currentGameEdit.dealer = appState.currentDealer
            appState.currentGame.append(currentGameEdit)
        }
    }
}
