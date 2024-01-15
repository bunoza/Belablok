package com.bunoza.belablok.data.repositories

import com.bunoza.belablok.data.database.GameDao
import com.bunoza.belablok.data.database.ScoreDao
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.SingleGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DatabaseRepository(private val scoreDao: ScoreDao, private val gameDao: GameDao) {

    suspend fun insertSingleGame(singleGame: SingleGame) {
        withContext(Dispatchers.IO) {
            scoreDao.insertSingleGame(singleGame)
        }
    }

    fun getAllSingleGames(): Flow<List<SingleGame>> {
        return scoreDao.getAllSingleGames().flowOn(Dispatchers.IO)
    }

    suspend fun deleteAllSingleGames() {
        withContext(Dispatchers.IO) {
            scoreDao.deleteSingleGame()
        }
    }

    suspend fun insertWholeGame(game: Game) {
        withContext(Dispatchers.IO) {
            gameDao.insertGame(game)
        }
    }

    fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAllGames().flowOn(Dispatchers.IO)
    }

    fun getGameById(id: Int): Flow<Game> {
        return gameDao.getGameById(id).flowOn(Dispatchers.IO)
    }

    suspend fun updateSingleGame(singleGame: SingleGame) {
        withContext(Dispatchers.IO) {
            scoreDao.updateSingleGame(singleGame)
        }
    }
}
