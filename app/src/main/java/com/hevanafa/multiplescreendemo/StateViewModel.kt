package com.hevanafa.multiplescreendemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

data class State (
    val page: Int = 0
)

class StateViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiStateFlow = _uiState.asStateFlow()

    fun setPage(newValue: Int) {
        _uiState.update {
            it.copy(page = newValue)
        }
    }

    @Composable
    fun getPage(): Int {
        return uiStateFlow.collectAsState().value.page
    }
}