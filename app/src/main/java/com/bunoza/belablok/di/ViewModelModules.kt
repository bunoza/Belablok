package com.bunoza.belablok.di

import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.ui.gamedetailsscreen.GameDetailsViewModel
import com.bunoza.belablok.ui.historyscreen.HistoryViewModel
import com.bunoza.belablok.ui.inputscorescreen.InputScoreViewModel
import com.bunoza.belablok.ui.scorescreen.ScoreScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
            (game: SingleGame?) ->
        InputScoreViewModel(get(), game)
    }
    viewModel { ScoreScreenViewModel(get(), get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel {
            (id: Int) ->
        GameDetailsViewModel(get(), id)
    }
}
