package com.seecolab.istudy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seecolab.istudy.R
import android.widget.Toast
import com.seecolab.istudy.ui.screens.learning.*
import com.seecolab.istudy.ui.screens.upload.UploadFlowScreen
import com.seecolab.istudy.ui.screens.worklist.StudentWorkListScreen
import com.seecolab.istudy.ui.navigation.Screen

data class LearningModule(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningNavigationScreen(parentNavController: NavController) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    val learningModules = listOf(
        LearningModule(
            title = stringResource(R.string.homework_correction),
            icon = Icons.Default.Edit,
            route = "homework_correction",
            description = "拍照批改作业，AI智能分析"
        ),
        LearningModule(
            title = stringResource(R.string.course_highlights),
            icon = Icons.Default.Star,
            route = "course_highlights",
            description = "课程重点讲解，章节学习"
        ),
        LearningModule(
            title = stringResource(R.string.question_bank),
            icon = Icons.Default.Star,
            route = "question_bank",
            description = "精选题库，智能组卷"
        ),
        LearningModule(
            title = "作业列表",
            icon = Icons.Default.List,
            route = "work_list",
            description = "查看与筛选作业"
        ),
        LearningModule(
            title = stringResource(R.string.exam_preparation),
            icon = Icons.Default.Star,
            route = "exam_preparation",
            description = "考前冲刺，知识点总结"
        ),
        LearningModule(
            title = "错题本",
            icon = Icons.Default.Star,
            route = "wrong_book",
            description = "管理错题与巩固练习"
        ),
        LearningModule(
            title = "学习趋势",
            icon = Icons.Default.ShowChart,
            route = "study_trend",
            description = "查看语文/数学/英语分数趋势"
        ),
        LearningModule(
            title = "学生画像",
            icon = Icons.Default.TrackChanges,
            route = "analysis_tab",
            description = "六边形画像图（随机分数70-95）"
        )
    )

    NavHost(
        navController = navController,
        startDestination = "learning_home"
    ) {
        composable("learning_home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.nav_learning_suggestions),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                
                


                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(learningModules.filter { it.route != "study_trend" && it.route != "analysis_tab" }) { module ->
                        val context = LocalContext.current
                        LearningModuleCard(
                            module = module,
                            onClick = {
                                if (module.route == "wrong_book") {
                                    // 跳转顶层导航的错题本页面
                                    parentNavController.navigate(com.seecolab.istudy.ui.navigation.Screen.WrongBook.createRoute())
                                } else {
                                    navController.navigate(module.route)
                                }
                            }
                        )
                    }
                }
            }
        }
        
        composable("homework_correction") {
            UploadFlowScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("course_highlights") {
            CourseHighlightsScreen(navController)
        }
        
        composable("question_bank") {
            QuestionBankScreen(navController)
        }
        
        composable("exam_preparation") {
            ExamPreparationScreen(navController)
        }

        composable("work_list") {
            StudentWorkListScreen(
                onWorkItemClick = { workItem ->
                    parentNavController.navigate(Screen.WorkDetail.createRoute(workItem.work_id))
                }
            )
        }
        
        // 内部 wrong_book 占位移除，统一使用顶层 WrongBook 路由
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningModuleCard(
    module: LearningModule,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = module.icon,
                contentDescription = module.title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = module.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}