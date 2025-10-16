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
import com.seecolab.istudy.data.model.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseHighlightsScreen(navController: NavController) {
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }

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
                text = stringResource(R.string.course_highlights),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Subject Selection
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_subject),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn {
                    items(Subject.values().toList()) { subject ->
                        FilterChip(
                            onClick = { selectedSubject = subject },
                            label = { Text(subject.displayName) },
                            selected = selectedSubject == subject,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // Chapter Content
        selectedSubject?.let { subject ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${subject.displayName} - 课程章节",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Sample chapters
                    val sampleChapters = listOf(
                        "第一章：基础概念",
                        "第二章：重要定理",
                        "第三章：实践应用",
                        "第四章：综合练习"
                    )

                    sampleChapters.forEach { chapter ->
                        ListItem(
                            headlineContent = { Text(chapter) },
                            leadingContent = {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            trailingContent = {
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        } ?: run {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(40.dp),
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
                        text = "请选择学科",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Text(
                        text = "选择学科后查看相关章节内容",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}