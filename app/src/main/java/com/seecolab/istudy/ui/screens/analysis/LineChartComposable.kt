package com.seecolab.istudy.ui.screens.analysis

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import kotlin.math.roundToInt

data class ChartPoint(val date: LocalDate, val value: Float)

/**
 * 轻量折线图（仅用于占位演示）：
 * - X 轴：日期（自动分布，绘制少量刻度）
 * - Y 轴：分数（minY..maxY）
 * - 折线 + 圆点
 */
@Composable
fun LineChartComposable(
    modifier: Modifier = Modifier,
    points: List<ChartPoint>,
    minY: Float = 60f,
    maxY: Float = 100f,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    axisColor: Color = MaterialTheme.colorScheme.outline,
    gridColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
    showDots: Boolean = true,
) {
    if (points.isEmpty()) return
    // 保证按日期排序
    val sorted = points.sortedBy { it.date }
    val start = sorted.first().date
    val end = sorted.last().date
    val totalDays = (java.time.temporal.ChronoUnit.DAYS.between(start, end)).coerceAtLeast(1)

    Canvas(modifier = modifier.fillMaxWidth().height(220.dp)) {
        val paddingLeft = 40f
        val paddingRight = 12f
        val paddingTop = 16f
        val paddingBottom = 28f

        val contentWidth = size.width - paddingLeft - paddingRight
        val contentHeight = size.height - paddingTop - paddingBottom

        // 轴线
        val x0 = paddingLeft
        val y0 = size.height - paddingBottom
        drawLine(
            color = axisColor,
            start = Offset(x0, y0),
            end = Offset(size.width - paddingRight, y0),
            strokeWidth = 2f
        )
        drawLine(
            color = axisColor,
            start = Offset(x0, paddingTop),
            end = Offset(x0, y0),
            strokeWidth = 2f
        )

        // 网格线 + Y 轴刻度
        val ySteps = 5
        val yStepValue = (maxY - minY) / ySteps
        for (i in 0..ySteps) {
            val value = minY + i * yStepValue
            val y = y0 - (value - minY) / (maxY - minY) * contentHeight
            drawLine(
                color = gridColor,
                start = Offset(x0, y),
                end = Offset(size.width - paddingRight, y),
                strokeWidth = 1f
            )
            // 简易文字：使用点代替（避免复杂文字绘制依赖）
            // 仅画一个小刻度标识
            drawLine(
                color = axisColor,
                start = Offset(x0 - 6f, y),
                end = Offset(x0, y),
                strokeWidth = 2f
            )
        }

        // X 轴刻度（最多 6 个）
        val maxXTicks = 6
        val dayStep = (totalDays / (maxXTicks - 1)).toInt().coerceAtLeast(1)
        var tickIndex = 0
        while (true) {
            val d = start.plusDays((tickIndex * dayStep).toLong())
            if (d > end) break
            val dx = (java.time.temporal.ChronoUnit.DAYS.between(start, d)).toFloat()
            val x = x0 + dx / totalDays * contentWidth
            drawLine(
                color = axisColor,
                start = Offset(x, y0),
                end = Offset(x, y0 + 6f),
                strokeWidth = 2f
            )
            tickIndex++
            if (tickIndex > maxXTicks + 2) break
        }

        // 折线路径
        val path = Path()
        sorted.forEachIndexed { index, p ->
            val dx = java.time.temporal.ChronoUnit.DAYS.between(start, p.date).toFloat()
            val x = x0 + dx / totalDays * contentWidth
            val y = y0 - (p.value - minY) / (maxY - minY) * contentHeight
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )

        if (showDots) {
            sorted.forEach { p ->
                val dx = java.time.temporal.ChronoUnit.DAYS.between(start, p.date).toFloat()
                val x = x0 + dx / totalDays * contentWidth
                val y = y0 - (p.value - minY) / (maxY - minY) * contentHeight
                drawCircle(
                    color = lineColor,
                    radius = 4f,
                    center = Offset(x, y)
                )
            }
        }
    }
}