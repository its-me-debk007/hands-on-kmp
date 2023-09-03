package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import network.httpClient
import repository.Repository
import repository.RepositoryImpl

class SharedViewModel(private val repository: Repository = RepositoryImpl()) : ScreenModel {

    private val _getMoviesFlow = MutableStateFlow<UiState>(UiState.Loading)
    val getMoviesFlow: StateFlow<UiState> get() = _getMoviesFlow

    private val _searchMoviesFlow = MutableStateFlow<UiState>(UiState.Loading)
    val searchMoviesFlow: StateFlow<UiState> get() = _searchMoviesFlow
    fun getMovies() {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                _searchMoviesFlow.value = UiState.Loading
                _getMoviesFlow.value = UiState.Success(repository.getMovies())
            } catch (e: Exception) {
                _getMoviesFlow.value = UiState.Failure(e.message.toString())
            }
        }
    }

    fun searchMovies(query: String) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                _searchMoviesFlow.value = UiState.Loading
                _searchMoviesFlow.value = UiState.Success(repository.searchMovies(query))
            } catch (e: Exception) {
                _searchMoviesFlow.value = UiState.Failure(e.message.toString())
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        httpClient.close()
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Failure(val errorMsg: String) : UiState()
}