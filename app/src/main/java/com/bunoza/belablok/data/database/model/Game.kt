package com.bunoza.belablok.data.database.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.yml.charts.common.model.Point
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val singleGameList: List<SingleGame>
) {
    val totalPointsWe: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                sum += it.scoreWe
            }
            return sum
        }

    val totalPointsThem: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                sum += it.scoreThem
            }
            return sum
        }
    val totalBasePointsWe: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                sum += it.afterBasePointsWe
            }
            return sum
        }
    val totalBasePointsThem: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                sum += it.afterBasePointsThem
            }
            return sum
        }
    val calledPointsWe: Int
        get() = totalPointsWe - totalBasePointsWe
    val calledPointsThem: Int
        get() = totalPointsThem - totalBasePointsThem

    val timesWeCalledShtiglia: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                if (it.shtigliaCalledWe) {
                    sum++
                }
            }
            return sum
        }
    val timesThemCalledShtiglia: Int
        get() {
            var sum = 0
            singleGameList.forEach {
                if (it.shtigliaCalledThem) {
                    sum++
                }
            }
            return sum
        }
    val timesWeFall: Int
        get() {
            var counter = 0
            singleGameList.forEach {
                if (it.whoCalled == "MI") {
                    if (it.scoreWe <= it.scoreThem) {
                        counter++
                    }
                }
            }
            return counter
        }
    val timesThemFall: Int
        get() {
            var counter = 0
            singleGameList.forEach {
                if (it.whoCalled == "VI") {
                    if (it.scoreWe >= it.scoreThem) {
                        counter++
                    }
                }
            }
            return counter
        }
    val shtigliaTakedownCounter: ShtigliaTakedownCounter
        get() {
            var shtigliaTakedownCounter = ShtigliaTakedownCounter(0, 0, 0, 0)
            singleGameList.forEach {
                if (it.shtigliaCalledWe) {
                    shtigliaTakedownCounter.timesWeCalledShtiglia++
                }
                if (it.shtigliaCalledThem) {
                    shtigliaTakedownCounter.timesTheyCalledShtiglia++
                }
                if (it.whoCalled == "MI") {
                    if (it.scoreWe <= it.scoreThem) {
                        shtigliaTakedownCounter.timesWeFall++
                    }
                }
                if (it.whoCalled == "VI") {
                    if (it.scoreWe >= it.scoreThem) {
                        shtigliaTakedownCounter.timesTheyFall++
                    }
                }
            }
            return shtigliaTakedownCounter
        }
}
data class ShtigliaTakedownCounter(
    var timesWeCalledShtiglia: Int,
    var timesTheyCalledShtiglia: Int,
    var timesWeFall: Int,
    var timesTheyFall: Int
)

fun List<Int>.toPairList(): List<Pair<Number, Number>> {
    var tempList = mutableListOf<Pair<Number, Number>>()
    var counter = 1
    this.forEach {
        tempList.add(Pair(counter.toFloat(), it.toFloat()))
        counter++
    }
    return tempList
}

fun List<Int>.toPointList(): List<Point> {
    var counter = 1
    var tempList = mutableListOf<Point>(Point(0F, 0F))
    this.forEach {
        tempList.add(Point(counter.toFloat(), it.toFloat()))
        counter++
    }
    Log.d(TAG, "toPointList: $tempList")
    Log.d(TAG, "BaseList: $this")
    return tempList
}
fun List<Int>.toNumberList(): List<Number> {
    var tempList = mutableListOf<Number>(0)
    this.forEach {
        tempList.add(it.toFloat())
    }
    return tempList
}
