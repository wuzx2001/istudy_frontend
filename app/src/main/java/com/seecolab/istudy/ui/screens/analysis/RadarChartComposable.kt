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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import android.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * 简易雷达图（六边形）
 * - labels: 指标名称（长度应与 values 一致）
 * - values: 指标数值（在 minValue..maxValue 内）
 * - minValue/maxValue：数值范围（默认 70..95）
 */
@Composable
fun RadarChartComposable(
    modifier: Modifier = Modifier,
    labels: List<String>,
    values: List<Float>,
    minValue: Float = 0f,
    maxValue: Float = 100f,
    gridLevels: Int = 5,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    fillColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
    gridColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    axisColor: Color = MaterialTheme.colorScheme.outline
) {
    if (labels.isEmpty() || values.isEmpty() || labels.size != values.size) return
    val n = labels.size
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val radius = min(size.width, size.height) * 0.38f
        val angleStep = 2f * PI.toFloat() / n

        fun pointFor(index: Int, r: Float): Offset {
            // 以 -90 度为起点，向右顺时针
            val angle = -PI.toFloat() / 2f + angleStep * index
            return Offset(
                x = cx + r * cos(angle),
                y = cy + r * sin(angle)
            )
        }

        // 绘制网格多边形
        for (level in 1..gridLevels) {
            val r = radius * (level / gridLevels.toFloat())
            val gridPath = Path()
            repeat(n) { i ->
                val p = pointFor(i, r)
                if (i == 0) gridPath.moveTo(p.x, p.y) else gridPath.lineTo(p.x, p.y)
            }
            gridPath.close()
            drawPath(
                path = gridPath,
                color = gridColor,
                style = Stroke(width = 1.5f)
            )
        }

        // 绘制轴线
        repeat(n) { i ->
            val p = pointFor(i, radius)
            drawLine(
                color = axisColor,
                start = Offset(cx, cy),
                end = p,
                strokeWidth = 1.2f,
                cap = StrokeCap.Round
            )
        }

        // 顶点标签（科目名称）
        val labelRadius = radius + 14.dp.toPx()
        repeat(n) { i ->
            val anchor = pointFor(i, labelRadius)
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    color = labelColor.toArgb()
                    textSize = 12.dp.toPx()
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                }
                // 轻微向下偏移，避免文字被点覆盖
                val y = anchor.y + paint.textSize / 3f
                canvas.nativeCanvas.drawText(labels[i], anchor.x, y, paint)
            }
        }

        // 绘制数据面
        val areaPath = Path()
        repeat(n) { i ->
            val v = values[i].coerceIn(minValue, maxValue)
            val ratio = (v - minValue) / (maxValue - minValue).coerceAtLeast(1e-6f)
            val p = pointFor(i, radius * ratio)
            if (i == 0) areaPath.moveTo(p.x, p.y) else areaPath.lineTo(p.x, p.y)
        }
        areaPath.close()

        // 填充和边框
        drawPath(path = areaPath, color = fillColor)
        drawPath(path = areaPath, color = lineColor, style = Stroke(width = 2.5f))

        // 顶点圆点
        repeat(n) { i ->
            val v = values[i].coerceIn(minValue, maxValue)
            val ratio = (v - minValue) / (maxValue - minValue).coerceAtLeast(1e-6f)
            val p = pointFor(i, radius * ratio)
            drawCircle(color = lineColor, radius = 4f, center = p)
        }
    }
}