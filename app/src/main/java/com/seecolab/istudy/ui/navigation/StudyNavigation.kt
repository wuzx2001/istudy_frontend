package com.seecolab.istudy.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.seecolab.istudy.data.model.UserRole
import com.seecolab.istudy.ui.screens.LearningNavigationScreen
import com.seecolab.istudy.ui.screens.ParentZoneScreen
import com.seecolab.istudy.ui.screens.ProfileScreen
import com.seecolab.istudy.ui.screens.TeacherRecommendationScreen
import com.seecolab.istudy.ui.screens.analysis.StudyTrendScreen
import com.seecolab.istudy.ui.screens.auth.LoginScreen
import com.seecolab.istudy.ui.screens.auth.SignupScreen
import com.seecolab.istudy.ui.screens.auth.RegistrationScreen
import com.seecolab.istudy.ui.screens.student.StudentRegistrationScreen
import com.seecolab.istudy.ui.screens.user.AddStudentScreen
import com.seecolab.istudy.ui.screens.user.AddParentScreen
import com.seecolab.istudy.ui.screens.user.UserInfoScreen
import com.seecolab.istudy.ui.screens.upload.UploadFlowScreen
import com.seecolab.istudy.ui.screens.worklist.StudentWorkListScreen
import com.seecolab.istudy.ui.screens.workdetail.StudentWorkDetailScreen
import com.seecolab.istudy.ui.viewmodel.AuthNavigationViewModel
import com.seecolab.istudy.ui.viewmodel.AuthNavigationEvent
import androidx.compose.ui.unit.dp

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Login : Screen("login", "登录", Icons.Default.Person)
    object Signup : Screen("signup", "注册", Icons.Default.Person)
    object TeacherRegistration : Screen("teacher_registration", "教师注册", Icons.Default.Person)
    object Registration : Screen("registration", "家庭注册", Icons.Default.Person)
    object StudentRegistration : Screen("student_registration", "学生注册", Icons.Default.Person)
    object Learning : Screen("learning", "学习建议", Icons.Default.Star)
    object Teachers : Screen("teachers", "推荐老师", Icons.Default.Person)
    object Parent : Screen("parent", "家长专区", Icons.Default.Home)
    object Profile : Screen("profile", "个人中心", Icons.Default.Person)
    object UserInfo : Screen("user_info", "用户管理", Icons.Default.Person)
    object AddStudent : Screen("add_student", "添加学生", Icons.Default.Person)
    object AddParent : Screen("add_parent", "添加家长", Icons.Default.Person)
    object Upload : Screen("upload", "上传作业", Icons.Default.Star)
    object Analysis : Screen("analysis", "学生画像", Icons.Default.List)
    object StudyTrend : Screen("study_trend", "学习趋势", Icons.Default.ShowChart)
    object WorkList : Screen("worklist", "作业列表", Icons.Default.List)
    object WrongBook : Screen("wrongbook?studentId={studentId}", "错题本", Icons.Default.Star) {
        fun createRoute(studentId: String? = null): String {
            return if (studentId.isNullOrBlank()) {
                "wrongbook"
            } else {
                "wrongbook?studentId=$studentId"
            }
        }
    }
    object WorkDetail : Screen("workdetail/{workId}", "作业详情", Icons.Default.Star) {
        fun createRoute(workId: String) = "workdetail/$workId"
    }
    object WrongDetail : Screen("wrongdetail/{workId}", "错题详情", Icons.Default.Star) {
        fun createRoute(workId: String) = "wrongdetail/$workId"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyNavigation() {
    val authViewModel: AuthNavigationViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Observe current user to determine authentication state
    val currentUser by authViewModel.authRepository.getCurrentUserFlow().collectAsStateWithLifecycle(initialValue = null)
    
    // Listen for authentication events
    LaunchedEffect(Unit) {
        authViewModel.navigationEvents.collect { event ->
            when (event) {
                is AuthNavigationEvent.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
    
    // Verify authentication when entering protected screens
    LaunchedEffect(currentDestination?.route) {
        val route = currentDestination?.route
        if (route != null && isProtectedRoute(route)) {
            authViewModel.verifyAuthenticationForScreen()
        }
    }
    
    // Filter bottom nav items based on user role
    val bottomNavItems = remember(currentUser) {
        when (currentUser?.role) {
            UserRole.STUDENT -> listOf(
                Screen.Learning,
                Screen.StudyTrend,
                Screen.Analysis,
                Screen.Teachers,
                Screen.Profile
            )
            UserRole.PARENT -> listOf(
                Screen.Parent,
                Screen.Profile
            )
            null -> emptyList()
        }
    }
    
    // Determine start destination based on authentication state
    val startDestination = when (val user = currentUser) {
        null -> Screen.Login.route
        else -> when (user.role) {
            UserRole.STUDENT -> Screen.Learning.route
            UserRole.PARENT -> Screen.Parent.route
        }
    }

    Scaffold(
        floatingActionButton = {
        },
        bottomBar = {
            val user = currentUser
            if (currentDestination?.route != Screen.StudentRegistration.route && 
                currentDestination?.route != Screen.Login.route && 
                currentDestination?.route != Screen.Signup.route &&
                currentDestination?.route != Screen.Registration.route && 
                user != null) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToStudentHome = {
                        navController.navigate(Screen.Learning.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToParentHome = {
                        navController.navigate(Screen.Parent.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignup = {
                        navController.navigate(Screen.Signup.route)
                    },
                    onNavigateToRegistration = {
                        navController.navigate(Screen.Registration.route)
                    },
                    onNavigateToTeacherRegistration = {
                        navController.navigate(Screen.TeacherRegistration.route)
                    }
                )
            }
            composable(Screen.Signup.route) {
                SignupScreen(
                    onNavigateToStudentHome = {
                        navController.navigate(Screen.Learning.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    },
                    onNavigateToParentHome = {
                        navController.navigate(Screen.Parent.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Registration.route) {
                RegistrationScreen(
                    onNavigateToStudentHome = {
                        navController.navigate(Screen.Learning.route) {
                            popUpTo(Screen.Registration.route) { inclusive = true }
                        }
                    },
                    onNavigateToParentHome = {
                        navController.navigate(Screen.Parent.route) {
                            popUpTo(Screen.Registration.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.StudentRegistration.route) {
                StudentRegistrationScreen(
                    onRegistrationComplete = {
                        navController.navigate(Screen.Learning.route) {
                            popUpTo(Screen.StudentRegistration.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Learning.route) {
                LearningNavigationScreen(navController)
            }
            composable(
                route = "wrongbook?studentId={studentId}",
                arguments = listOf(navArgument("studentId") { type = NavType.StringType; nullable = true; defaultValue = null })
            ) { backStackEntry ->
                val studentIdArg = backStackEntry.arguments?.getString("studentId")
                com.seecolab.istudy.ui.screens.wrongbook.WrongBookScreen(
                    initialStudentId = studentIdArg,
                    onWorkItemClick = { workItem ->
                        navController.navigate(Screen.WrongDetail.createRoute(workItem.work_id))
                    }
                )
            }
            composable(Screen.Teachers.route) {
                TeacherRecommendationScreen(
                    onNavigateToTeacherDetail = { userId ->
                        navController.navigate("teacher_detail/$userId")
                    }
                )
            }
            composable(Screen.Analysis.route) {
                com.seecolab.istudy.ui.screens.analysis.AnalysisScreen(
                    onWorkItemClick = { workItem ->
                        navController.navigate(Screen.WrongDetail.createRoute(workItem.work_id))
                    }
                )
            }
            // 学习趋势页面
            composable(Screen.StudyTrend.route) {
                StudyTrendScreen()
            }
            composable(Screen.Parent.route) {
                ParentZoneScreen(
                    onNavigateToUserInfo = {
                        navController.navigate(Screen.UserInfo.route)
                    },
                    onWorkItemClick = { workItem ->
                        navController.navigate(Screen.WorkDetail.createRoute(workItem.work_id))
                    },
                    onNavigateToParentWorkList = { student ->
                        navController.navigate("upload_worklist/${student.user_id}")
                    },
                    onNavigateToWrongBook = { student ->
                        navController.navigate(Screen.WrongBook.createRoute(student.user_id))
                    },
                    onNavigateToStudyTrend = {
                        navController.navigate(Screen.StudyTrend.route)
                    },
                    onNavigateToAnalysis = {
                        navController.navigate(Screen.Analysis.route)
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.UserInfo.route) {
                UserInfoScreen(
                    onNavigateToAddStudent = {
                        navController.navigate(Screen.AddStudent.route)
                    },
                    onNavigateToAddParent = {
                        navController.navigate(Screen.AddParent.route)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.AddStudent.route) {
                AddStudentScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.AddParent.route) {
                AddParentScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.TeacherRegistration.route) {
                com.seecolab.istudy.ui.screens.teacher.TeacherRegistrationScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.WorkList.route) {
                StudentWorkListScreen(
                    onWorkItemClick = { workItem ->
                        navController.navigate(Screen.WorkDetail.createRoute(workItem.work_id))
                    },
                    onUploadClick = {
                        // This will be handled in the Learning section
                    }
                )
            }
            composable(
                route = Screen.WorkDetail.route,
                arguments = listOf(navArgument("workId") { type = NavType.StringType })
            ) { backStackEntry ->
                val workId = backStackEntry.arguments?.getString("workId") ?: ""
                StudentWorkDetailScreen(
                    workId = workId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Screen.WrongDetail.route,
                arguments = listOf(navArgument("workId") { type = NavType.StringType })
            ) { backStackEntry ->
                val workId = backStackEntry.arguments?.getString("workId") ?: ""
                com.seecolab.istudy.ui.screens.wrongbook.WrongDetailScreen(
                    workId = workId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Upload.route) {
                UploadFlowScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            // 家长专区 - 作业列表（带服务端筛选）
            composable(
                route = "upload_worklist/{studentId}",
                arguments = listOf(navArgument("studentId") { type = NavType.StringType })
            ) { backStackEntry ->
                val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
                com.seecolab.istudy.ui.screens.upload.UploadWorkListScreen(
                    studentId = studentId,
                    onWorkItemClick = { workItem ->
                        navController.navigate(Screen.WorkDetail.createRoute(workItem.work_id))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            // 教师详情页（移入 NavHost 内部）
            composable(
                route = "teacher_detail/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                com.seecolab.istudy.ui.screens.teacher.TeacherDetailScreen(
                    userId = userId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

/**
 * Determines if a route requires authentication
 */
private fun isProtectedRoute(route: String): Boolean {
    return when (route) {
        Screen.Login.route,
        Screen.Signup.route,
        Screen.Registration.route,
        Screen.StudentRegistration.route,
        Screen.TeacherRegistration.route -> false
        else -> true // All other routes require authentication
    }
}