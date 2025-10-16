package com.seecolab.istudy.ui.screens.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.ui.screens.worklist.StudentWorkListScreen
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrackChanges
import com.seecolab.istudy.ui.screens.LearningModule
import com.seecolab.istudy.ui.screens.LearningModuleCard
import kotlin.random.Random
import androidx.compose.runtime.saveable.rememberSaveable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    onWorkItemClick: (StudentWorkListItem) -> Unit
) {
    // 学生画像单页展示（底部栏“学生画像”）
    StudentPortraitScreen()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudentPortraitScreen() {
    // 固定总体评价（后端未完成时作为占位）
    val evaluationText = """
        学生整体学习情况良好：语文、数学、英语基础扎实，理科科目（物理、生物）有稳定发挥，地理略显薄弱但具备提升空间。
        建议策略：
        1) 语数英：保持错题整理与每周一次阶段复盘，关注理解性与迁移性题型；
        2) 物理/生物：通过小实验与情境题训练应用能力，强化模型化思维；
        3) 地理：补齐概念与地图识读训练，配合时事材料提升综合分析能力；
        4) 作息：确保每天30–45分钟高质量巩固与错题回顾，周末进行一次综合检视。
        下阶段目标：在保持优势科目稳定的基础上，将地理与英语的薄弱知识点各提升1个层级。
    """.trimIndent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "学生画像",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 六边形雷达图：语文/数学/英语/物理/地理/生物，随机 70-95（一次生成，保存状态，绘图与标签共用）
        val labels = listOf("语文", "数学", "英语", "物理", "地理", "生物")
        val scores = rememberSaveable {
            val rnd = Random(System.currentTimeMillis())
            List(labels.size) { 70 + rnd.nextInt(26) } // 70..95（Int，saveable）
        }
        val values = remember(scores) { scores.map { it.toFloat() } }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("知识掌握雷达图", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                RadarChartComposable(
                    labels = labels,
                    values = values,
                    minValue = 0f,
                    maxValue = 100f
                )
                Spacer(Modifier.height(8.dp))
                // 简单分数行
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3
                ) {
                    labels.forEachIndexed { i, name ->
                        AssistChip(
                            onClick = {},
                            label = { Text("$name ${scores[i]}") }
                        )
                    }
                }
            }
        }

        // 总体评价
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("总体评价", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = evaluationText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}