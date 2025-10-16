package com.seecolab.istudy.ui.screens.teacher

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import com.seecolab.istudy.data.api.TeacherService
import com.seecolab.istudy.data.api.TeacherRegisterRequest
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherRegistrationScreen(
    onNavigateBack: () -> Unit = {},
    teacherService: TeacherService = androidx.hilt.navigation.compose.hiltViewModel<TempProviderVM>().teacherService
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("") }
    var realName by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("male") } // male/female
    val gradeOptions = (1..9).map { "grade_$it" }
    val gradeLabels = (1..9).map { "${it}年级" }
    var selectedGrades by remember { mutableStateOf(setOf<String>()) }

    val subjectOptions = listOf("语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
    var selectedSubjects by remember { mutableStateOf(setOf<String>()) }

    var telephone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") } // "yyyy-MM-dd"

    var isSubmitting by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var gradeExpanded by remember { mutableStateOf(false) }
    var subjectExpanded by remember { mutableStateOf(false) }

    fun validate(): String? {
        if (userName.isBlank()) return "请输入用户名"
        if (realName.isBlank()) return "请输入真实姓名"
        if (selectedGrades.isEmpty()) return "请选择任教年级"
        if (selectedSubjects.isEmpty()) return "请选择学科（最多三门）"
        if (selectedSubjects.size > 3) return "学科最多选择三门"
        if (telephone.isBlank()) return "请输入手机号码"
        if (password.isBlank()) return "请输入密码"
        if (birthday.isBlank()) return "请选择出生日期"
        return null
    }

    suspend fun submit(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val birthdayIso = "${birthday}T00:00:00"
            val req = TeacherRegisterRequest(
                user_name = userName.trim(),
                real_name = realName.trim(),
                sex = sex,
                grade = selectedGrades.toList(),
                subject = selectedSubjects.toList(),
                telephone = telephone.trim(),
                address = address.trim(),
                password = password,
                birthday = birthdayIso
            )
            val resp = teacherService.register(req)
            if (resp.isSuccessful) {
                Result.success(resp.body()?.message ?: "注册成功")
            } else {
                Result.failure(Exception("注册失败(${resp.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        birthday = sdf.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("取消") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("教师注册") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) { Text("返回") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = userName, onValueChange = { userName = it },
                label = { Text("用户名") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = realName, onValueChange = { realName = it },
                label = { Text("真实姓名") }, modifier = Modifier.fillMaxWidth()
            )

            // 性别
            Text("性别", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    selected = sex == "male",
                    onClick = { sex = "male" },
                    label = { Text("男") }
                )
                FilterChip(
                    selected = sex == "female",
                    onClick = { sex = "female" },
                    label = { Text("女") }
                )
            }

            // 年级多选（下拉）
            ExposedDropdownMenuBox(
                expanded = gradeExpanded,
                onExpandedChange = { gradeExpanded = !gradeExpanded }
            ) {
                val selectedGradeLabels = selectedGrades
                    .mapNotNull { key -> gradeOptions.indexOf(key).takeIf { it >= 0 }?.let { gradeLabels[it] } }
                    .sorted()
                OutlinedTextField(
                    value = if (selectedGradeLabels.isEmpty()) "请选择年级" else selectedGradeLabels.joinToString("、"),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("年级（可多选）") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = gradeExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = gradeExpanded,
                    onDismissRequest = { gradeExpanded = false }
                ) {
                    gradeOptions.forEachIndexed { idx, key ->
                        val label = gradeLabels[idx]
                        val checked = selectedGrades.contains(key)
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = checked, onCheckedChange = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text(label)
                                }
                            },
                            onClick = {
                                selectedGrades = selectedGrades.toMutableSet().apply {
                                    if (checked) remove(key) else add(key)
                                }
                            }
                        )
                    }
                }
            }

            // 学科多选（最多3门，下拉）
            ExposedDropdownMenuBox(
                expanded = subjectExpanded,
                onExpandedChange = { subjectExpanded = !subjectExpanded }
            ) {
                val subjectSummary = if (selectedSubjects.isEmpty()) {
                    "请选择学科（最多3门）"
                } else {
                    selectedSubjects.joinToString("、")
                }
                OutlinedTextField(
                    value = subjectSummary,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("学科（最多3门）") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = subjectExpanded,
                    onDismissRequest = { subjectExpanded = false }
                ) {
                    subjectOptions.forEach { sub ->
                        val checked = selectedSubjects.contains(sub)
                        val canSelectMore = selectedSubjects.size < 3 || checked
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = checked, onCheckedChange = null, enabled = canSelectMore)
                                    Spacer(Modifier.width(8.dp))
                                    Text(sub, color = if (canSelectMore) LocalContentColor.current else LocalContentColor.current.copy(alpha = 0.4f))
                                }
                            },
                            enabled = canSelectMore,
                            onClick = {
                                if (checked) {
                                    selectedSubjects = selectedSubjects - sub
                                } else if (canSelectMore) {
                                    selectedSubjects = selectedSubjects + sub
                                }
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = telephone, onValueChange = { telephone = it },
                label = { Text("手机号码") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address, onValueChange = { address = it },
                label = { Text("家庭住址") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password, onValueChange = { password = it },
                label = { Text("密码") }, modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            // 出生日期
            OutlinedTextField(
                value = birthday, onValueChange = {},
                label = { Text("出生日期") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Filled.CalendarToday, contentDescription = "选择日期")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val err = validate()
                    if (err != null) {
                        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isSubmitting = true
                    scope.launch {
                        val res = submit()
                        isSubmitting = false
                        res.onSuccess {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            onNavigateBack()
                        }.onFailure {
                            Toast.makeText(context, it.message ?: "注册失败", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                enabled = !isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text("提交注册")
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

/**
 * 简易 FlowRow（如果项目已引入 androidx.compose.foundation.layout.FlowRow 可替换）
 */
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    // 直接复用 Column+Row 的简单多行布局：为避免复杂度，这里使用 FlowRow 版本依赖（若已引入正式版本请替换）
    Column(modifier = modifier) {
        // 简化：直接一行行Wrap，若已引入正式 FlowRow，请移除此占位
        Row(horizontalArrangement = horizontalArrangement, verticalAlignment = Alignment.CenterVertically) {
            content()
        }
    }
}