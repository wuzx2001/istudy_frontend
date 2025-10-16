package com.seecolab.istudy.ui.screens.wrongbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.WrongDetailResponse
import com.seecolab.istudy.data.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WrongDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val detail: WrongDetailResponse? = null
)

@HiltViewModel
class WrongDetailViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WrongDetailUiState(isLoading = false))
    val uiState: StateFlow<WrongDetailUiState> = _uiState

    fun loadWrongDetail(workId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val result = uploadRepository.getWrongDetail(workId)
            result.fold(
                onSuccess = { resp ->
                    _uiState.value = WrongDetailUiState(isLoading = false, detail = resp)
                },
                onFailure = { e ->
                    _uiState.value = WrongDetailUiState(isLoading = false, errorMessage = e.message ?: "加载失败")
                }
            )
        }
    }
}