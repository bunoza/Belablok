
import Foundation

class AppState: ObservableObject {
    static let shared = AppState()
    
    @CustomUserDefaultsObject("finished_games", defaultValue: [:])
    var finishedGames: [Int: [Game]]
    
    @CustomUserDefaultsObject("current_game", defaultValue: [])
    var currentGame: [Game]
}

