package com.bunoza.belablok.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class SingleGame(
    @PrimaryKey(autoGenerate = true)
    val gameId: Int = 0,
    val baseGamePointsWe: Int,
    val baseGamePointsThem: Int,
    val callTwentyWe: Int,
    val callTwentyThem: Int,
    val callFiftyWe: Int,
    val callFiftyThem: Int,
    val callHundredWe: Int,
    val callHundredThem: Int,
    val callBelotWe: Int,
    val callBelotThem: Int,
    val scoreWe: Int,
    val scoreThem: Int,
    val afterBasePointsWe:Int,
    val afterBasePointsThem:Int,
    val dealer:String
){
    val accumulatedCallsWe:Int get() {
        return callTwentyWe*20 + callFiftyWe*50 + callHundredWe*100 + callBelotWe*1000
    }
    val accumulatedCallsThem:Int get() {
        return callTwentyThem*20 + callFiftyThem*50 + callHundredThem*100 + callBelotThem*1000
    }

}
