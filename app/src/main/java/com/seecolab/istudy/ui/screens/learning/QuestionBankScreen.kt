package com.seecolab.istudy.ui.screens.learning

import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionBankScreen(navController: NavController) {
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
                text = stringResource(R.string.question_bank),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "智能组卷",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Text(
                    text = "AI根据你的学习情况生成个性化试卷",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = { /* TODO: Implement test generation */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Create, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.generate_test))
                }
            }
        }

        Text(
            text = "功能特色",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val features = listOf(
            "海量精选题库覆盖所有知识点",
            "AI智能分析学习薄弱环节",
            "个性化推荐练习题目",
            "实时调整题目难度",
            "详细的答案解析和知识点讲解"
        )

        features.forEach { feature ->
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = feature,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}