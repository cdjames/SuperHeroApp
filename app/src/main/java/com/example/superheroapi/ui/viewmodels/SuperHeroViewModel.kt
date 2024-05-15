package com.example.superheroapi.ui.viewmodels

import android.util.Log
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

class SuperHeroViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {
    var superHeroUiState: SuperHeroUiState by mutableStateOf(SuperHeroUiState.Loading)
        private set
    private val apiKey = BuildConfig.API_KEY

    fun getHero(id: String) {
        viewModelScope.launch {
            superHeroUiState = try {
                val result = superHeroRepository.getHero(apiKey, id) //TODO
                SuperHeroUiState.Success(result)
            } catch (e: IOException) {
                // I added a Log statement here and in the catch block below to help troubleshoot
                // it printed an Error 404 to the Log, which helped me realize you forgot to change the
                // URL from the network interface you copied and pasted
                Log.d("error", e.toString())
                SuperHeroUiState.Error
            } catch (e: HttpException) {
                Log.d("error", e.toString())
                SuperHeroUiState.Error
            }
        }
    }

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