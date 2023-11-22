package com.bunoza.belablok.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.SingleGame

@TypeConverters(ListConverter::class)
@Database(entities = [SingleGame::class, Game::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
    abstract fun gameDao(): GameDao
}
