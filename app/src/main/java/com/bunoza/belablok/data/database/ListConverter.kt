package com.bunoza.belablok.data.database

import androidx.room.TypeConverter
import com.bunoza.belablok.data.database.model.SingleGame
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListConverter {

    @TypeConverter
    fun fromGameToString(singleGame: SingleGame): String {
        return Json.encodeToString(singleGame)
    }

    @TypeConverter
    fun fromStringToGame(string: String): SingleGame {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromGameListToString(singleGameList: List<SingleGame>): String {
        return Json.encodeToString(singleGameList)
    }

    @TypeConverter
    fun fromStringToGameList(string: String): List<SingleGame> {
        return Json.decodeFromString(string)
    }
}
