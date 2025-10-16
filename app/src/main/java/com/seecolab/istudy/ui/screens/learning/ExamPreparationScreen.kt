package com.seecolab.istudy.ui.screens.learning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seecolab.istudy.R
import com.seecolab.istudy.data.model.ExamType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamPreparationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "返回")
            }
            
            Text(
                text = stringResource(R.string.exam_preparation),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = "选择考试类型",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val examTypes = listOf(
            ExamType.UNIT to "单元考试",
            ExamType.MIDTERM to "期中考试", 
            ExamType.FINAL to "期末考试"
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(examTypes) { (examType, displayName) ->
                ExamTypeCard(
                    title = displayName,
                    examType = examType,
                    onClick = { /* TODO: Navigate to exam prep details */ }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamTypeCard(
    title: String,
    examType: ExamType,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (examType) {
                    ExamType.UNIT -> Icons.Default.Star
                    ExamType.MIDTERM -> Icons.Default.Star
                    ExamType.FINAL -> Icons.Default.Star
                    ExamType.PRACTICE -> Icons.Default.Edit
                },
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Text(
                    text = when (examType) {
                        ExamType.UNIT -> "单章节重点复习"
                        ExamType.MIDTERM -> "半学期知识梳理"
                        ExamType.FINAL -> "全学期综合复习"
                        ExamType.PRACTICE -> "日常练习巩固"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}