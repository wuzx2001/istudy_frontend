package com.seecolab.istudy.ui.screens.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun StudyTrendScreen() {
    // 默认日期范围：2025-02-01 至 今日
    val startDate = remember { LocalDate.of(2025, 2, 1) }
    val endDate = remember { safeToday() }

    // 模拟数据生成（60-100），每日一个点
    val subjects = listOf("语文", "数学", "英语")
    val subjectColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.secondary
    )

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "学习趋势（${startDate.format(formatter)} 至 ${endDate.format(formatter)}）",
            style = MaterialTheme.typography.titleLarge
        )

        subjects.forEachIndexed { idx, subject ->
            val points = remember(startDate, endDate, subject) {
                generateRandomPoints(startDate, endDate, seedKey = subject)
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = subject,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    LineChartComposable(
                        points = points,
                        minY = 70f,
                        maxY = 95f,
                        lineColor = subjectColors[idx % subjectColors.size],
                        showDots = true
                    )
                }
            }
        }
    }
}

/**
 * 安全获取“今天”的日期，避免底层时钟源（如网络授时）不可用导致异常
 */
private fun safeToday(): LocalDate {
    // 仅使用本地设备时钟与本地时区，完全不依赖网络授时
    return Instant.ofEpochMilli(System.currentTimeMillis())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

private fun generateRandomPoints(
    start: LocalDate,
    end: LocalDate,
    seedKey: String
): List<ChartPoint> {
    val totalDays = java.time.temporal.ChronoUnit.DAYS.between(start, end).toInt().coerceAtLeast(1)
    val count = 10
    val step = if (count > 1) totalDays.toDouble() / (count - 1) else 0.0
    val rnd = Random(seedKey.hashCode())
    val list = ArrayList<ChartPoint>(count)
    for (i in 0 until count) {
        val offset = kotlin.math.round(i * step).toLong().coerceIn(0, totalDays.toLong())
        val date = start.plusDays(offset)
        val value = 70 + rnd.nextInt(26) // 70..95
        list.add(ChartPoint(date, value.toFloat()))
    }
    return list
}