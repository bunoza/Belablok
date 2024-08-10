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
    val afterBasePointsWe: Int,
    val afterBasePointsThem: Int,
    val dealer: String,
    val whoCalled:String,
    val shtigliaCalledWe:Boolean,
    val shtigliaCalledThem:Boolean,
) {
    val accumulatedCallsWe: Int get() {
        val shtigliaWe = if(shtigliaCalledWe){90}else 0
        return callTwentyWe * 20 + callFiftyWe * 50 + callHundredWe * 100 + callBelotWe * 1001 + shtigliaWe
    }
    val accumulatedCallsThem: Int get() {
        val shtigliaThem = if(shtigliaCalledThem){90}else 0
        return callTwentyThem * 20 + callFiftyThem * 50 + callHundredThem * 100 + callBelotThem * 1001 + shtigliaThem
    }
}
