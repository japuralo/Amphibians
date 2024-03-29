package com.example.amphibians.ui.screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibianRepository
import com.example.amphibians.data.DefaulAmphibiansRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface AmphibiansUiState
{
    data class Success(val amphibians: List<Amphibian>): AmphibiansUiState
    object Error: AmphibiansUiState
    object Loading: AmphibiansUiState
}

class AmphibiansViewModel(
    private val amphibiansRepository: AmphibianRepository
): ViewModel()
{
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
    private set

    init {
        getAmphibians()
    }

    fun getAmphibians()
    {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try
            {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
            }
            catch (e:Exception)
            {
                AmphibiansUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibianRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}
