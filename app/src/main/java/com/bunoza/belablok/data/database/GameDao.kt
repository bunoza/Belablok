package com.bunoza.belablok.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bunoza.belablok.data.database.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<Game>>

    @Query("SELECT * from game where id=:id")
    fun getGameById(id: Int): Flow<Game>

    @Delete
    suspend fun deleteGame(game: Game)
}
