package ru.roomhello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(private val repository: GreetingRepository) :
    ViewModel() {
    val uiState: StateFlow<String> = repository.getGreeting()
        .map { entity -> entity?.message ?: "Загрузка..." }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Загрузка"
        )

    fun updateGreetingText(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGreeting(GreetingEntity(id = 1, message = newText))

        }
    }


}