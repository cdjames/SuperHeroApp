package com.example.superheroapi.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.superheroapi.BuildConfig
import com.example.superheroapi.SuperHeroApplication
import com.example.superheroapi.data.SuperHeroRepository
import com.example.superheroapi.model.SuperHero
import com.example.superheroapi.model.SuperHeroSearch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SuperHeroUiState {
    data class Success(val hero: SuperHero) : SuperHeroUiState
    data object Loading : SuperHeroUiState
    data object Error : SuperHeroUiState
}

sealed interface SuperHeroSearchUiState {
    data class Success(val result: SuperHeroSearch) : SuperHeroSearchUiState
    data object Loading : SuperHeroSearchUiState
    data object Error : SuperHeroSearchUiState
}

class SuperHeroViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {
    var superHeroUiState: SuperHeroUiState by mutableStateOf(SuperHeroUiState.Loading)
        private set
    var superHeroSearchUiState: SuperHeroSearchUiState by mutableStateOf(SuperHeroSearchUiState.Loading)
        private set
    private val apiKey = BuildConfig.API_KEY

    fun searchHero(name: String) {
        viewModelScope.launch {
            superHeroSearchUiState = try {
                val result = superHeroRepository.searchHero(apiKey, name)
                SuperHeroSearchUiState.Success(result)
            } catch (e: IOException) {
                SuperHeroSearchUiState.Error
            } catch (e: HttpException) {
                SuperHeroSearchUiState.Error
            }
        }
    }

    fun getHero(id: String) {
        viewModelScope.launch {
            superHeroUiState = try {
                val result = superHeroRepository.getHero(apiKey, id) //TODO
                SuperHeroUiState.Success(result)
            } catch (e: IOException) {
                SuperHeroUiState.Error
            } catch (e: HttpException) {
                SuperHeroUiState.Error
            }
        }
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SuperHeroApplication)
                val superHeroRepository = application.container.superHeroRepo
                SuperHeroViewModel(superHeroRepository = superHeroRepository)
            }
        }
    }
}