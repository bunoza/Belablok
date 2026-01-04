import Foundation
import SwiftUI

class AppState: ObservableObject {
    static let shared = AppState()

    @CustomUserDefaultsObject("finished_games", defaultValue: [])
    var finishedGames: [[Game]]

    @CustomUserDefaultsObject("current_game", defaultValue: [])
    var currentGame: [Game]

    @CustomUserDefaultsObject("current_dealer", defaultValue: .me)
    var currentDealer: Dealer

    @CustomUserDefaultsObject("should_keep_screen_on", defaultValue: UIApplication.shared.isIdleTimerDisabled)
    var isIdleTimerDisabled: Bool

    @CustomUserDefaultsObject("should_show_score_difference_on_main", defaultValue: true)
    var shouldShowScoreDifferenceOnMain: Bool

    @CustomUserDefaultsObject("should_show_score_difference_on_history", defaultValue: true)
    var shouldShowScoreDifferenceOnHistory: Bool

    @CustomUserDefaultsObject("game_end_score", defaultValue: PossibleGameEndScore.large)
    var gameEndScore: PossibleGameEndScore

    @CustomUserDefaultsObject("stiglja_value", defaultValue: StigljaValue.ninety)
    var stigljaValue: StigljaValue

    @CustomUserDefaultsObject("ask_for_review_counter", defaultValue: 0)
    var reviewCounter: Int

    @CustomUserDefaultsObject("odabrana_zvanja", defaultValue: [.dvadeset, .pedeset, .sto, .belot])
    var selectedCalls: [Calls]

    @CustomUserDefaultsObject("odabran_zadani_pozivatelj", defaultValue: .we)
    var defaultCallerSelection: Caller
}
