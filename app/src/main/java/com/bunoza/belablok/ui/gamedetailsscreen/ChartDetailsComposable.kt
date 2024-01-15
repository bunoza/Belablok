package com.bunoza.belablok.ui.gamedetailsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun ChartDetailsComposable(wePointsData: List<Point>, themPointsData: List<Point>) {
    val configuration = LocalConfiguration.current
    val screenWidthInDp = configuration.screenWidthDp.dp
    val xAxisData = AxisData.Builder()
        .steps(wePointsData.count() - 1)
        .axisStepSize(screenWidthInDp / wePointsData.size)
        .labelData {
            it.toString()
        }
        .backgroundColor(Color.Transparent)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(wePointsData.size)
        .backgroundColor(Color.Transparent)
        .labelData {
            val yScale = 1000 / wePointsData.count()
            (it * yScale).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.primary)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = wePointsData,
                    LineStyle(LineType.Straight(isDotted = false), color = Color.Blue),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(Color.Transparent),
                    null
                ),
                Line(
                    dataPoints = themPointsData,
                    LineStyle(LineType.Straight(isDotted = false), color = Color.Red),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(Color.Transparent),
                    null
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = MaterialTheme.colorScheme.background,
        isZoomAllowed = false
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        LineChart(modifier = Modifier.fillMaxWidth(), lineChartData = lineChartData)
    }
}
