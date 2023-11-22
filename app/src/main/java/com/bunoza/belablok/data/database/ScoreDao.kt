package com.bunoza.belablok.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.bunoza.belablok.data.database.model.SingleGame
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insertSingleGame(singleGame: SingleGame)

    @Query("SELECT * FROM singlegame")
    fun getAllSingleGames(): Flow<List<SingleGame>>

    @Query("DELETE from singlegame")
    suspend fun deleteSingleGame()

    @Upsert
    suspend fun updateSingleGame(singleGame: SingleGame)
}
