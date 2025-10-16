package com.seecolab.istudy.ui.screens.teacher

import androidx.lifecycle.ViewModel
import com.seecolab.istudy.data.api.TeacherService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempProviderVM @Inject constructor(
    val teacherService: TeacherService
) : ViewModel()