package com.bunoza.belablok.ui.gamedetailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.repositories.DatabaseRepository
import com.bunoza.belablok.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameDetailsViewModel(private val databaseRepository: DatabaseRepository,private val id:Int):ViewModel() {

    private var _uiState= MutableStateFlow<UIState>(UIState.Loading)
    val uiState=_uiState.asStateFlow()
    private var _wePointsList = MutableStateFlow<List<Int>>(emptyList())
    val wePointsList = _wePointsList.asStateFlow()
    private var _themPointsList = MutableStateFlow<List<Int>>(emptyList())
    val themPointsList = _themPointsList.asStateFlow()

    init {
        getGameById()
    }


    fun getGameById(){
        viewModelScope.launch {
            try {
                databaseRepository.getGameById(id).collect{game->
                    _wePointsList.value=getPointsForGraph(game)
                    _themPointsList.value=getPointsForGraphThem(game)
                    _uiState.value=UIState.Success(data = game)
                }
            }
            catch (e:Exception){
                _uiState.value=UIState.Error
            }
        }
    }

    fun getPointsForGraph(game: Game):List<Int>{
        var tempList:MutableList<Int> = mutableListOf()
        var previousSum=0
        game.singleGameList.forEach {
            tempList.add(previousSum.plus(it.scoreWe))
            previousSum+=it.scoreWe
        }
        return tempList
    }
    fun getPointsForGraphThem(game: Game):List<Int>{
        var tempList:MutableList<Int> = mutableListOf()
        var previousSum=0
        game.singleGameList.forEach {
            tempList.add(previousSum.plus(it.scoreThem))
            previousSum+=it.scoreThem
        }
        return tempList
    }

}