import SwiftUI
import Foundation

class AppState: ObservableObject {
    static let shared = AppState()
    
    @CustomUserDefaultsObject("finished_games", defaultValue: [])
    var finishedGames: [[Game]]
    
    @CustomUserDefaultsObject("current_game", defaultValue: [])
    var currentGame: [Game]
    
    @CustomUserDefaultsObject("current_dealer", defaultValue: .me)
    var currentDealer: Dealer
}

