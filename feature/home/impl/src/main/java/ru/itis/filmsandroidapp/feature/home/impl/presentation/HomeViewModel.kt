package ru.itis.filmsandroidapp.feature.home.impl.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.filmsandroidapp.core.navigation.Navigator
import ru.itis.filmsandroidapp.feature.auth.api.model.User
import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel
import ru.itis.filmsandroidapp.feature.home.api.navigation.HomeDestination.AUTH_USERNAME_PARAM

import ru.itis.filmsandroidapp.feature.home.api.usecase.GetFilmsUseCase
import javax.inject.Inject

@Immutable
data class HomeState(
    val users: List<User> = listOf(),
    val username: String = "Гость",
    val searchValue: String = "",
    val searchByName: Boolean = true,
    val isLoading: Boolean = true,
    val error: String = "",
    val currentPage: Int = 1,
    val data: ShortFilmResponseModel = ShortFilmResponseModel(0,0, 0, emptyList())
)

sealed interface HomeEvent {
    object OnLaunch : HomeEvent
    object OnLoginBtnClick : HomeEvent
    object OnNextPageBtnClick : HomeEvent
    object OnBackPageBtnClick : HomeEvent
    data class OnFilmCardClick(val filmId: Int) : HomeEvent
    data class OnUsernameClick(val username: String) : HomeEvent
}

sealed interface HomeSideEffect {
    object NavigateToLoginScreen : HomeSideEffect
    data class NavigateToProfileScreen(val username: String) : HomeSideEffect
    data class NavigateToFilmDetailsScreen(val filmId: Int) : HomeSideEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel(), Navigator by navigator {

    private val username: String = savedStateHandle[AUTH_USERNAME_PARAM] ?: "Гость"

    private val _state: MutableStateFlow<HomeState> =
        MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _action = MutableSharedFlow<HomeSideEffect?>()
    val action: SharedFlow<HomeSideEffect?>
        get() = _action.asSharedFlow()

    fun event(homeEvent: HomeEvent) {
        ViewModelProvider.NewInstanceFactory
        when (homeEvent) {
            HomeEvent.OnLaunch -> onLaunch()
            HomeEvent.OnLoginBtnClick -> onLoginBtnClick()
            HomeEvent.OnNextPageBtnClick -> onNextPageBtnClick()
            HomeEvent.OnBackPageBtnClick -> onBackPageBtnClick()
            is HomeEvent.OnFilmCardClick -> onFilmCardClick(homeEvent.filmId)
            is HomeEvent.OnUsernameClick -> onUsernameClick(homeEvent.username)
            else -> {}
        }
    }



    private fun onUsernameClick(username: String){
        viewModelScope.launch {
            _action.emit(HomeSideEffect.NavigateToProfileScreen(username))
        }
    }

    private fun onLoginBtnClick(){
        viewModelScope.launch {
            _action.emit(HomeSideEffect.NavigateToLoginScreen)
        }
    }

    private fun onFilmCardClick(filmId: Int){
        viewModelScope.launch {
            _action.emit(HomeSideEffect.NavigateToFilmDetailsScreen(filmId))
        }
    }

    private fun onNextPageBtnClick() {

        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    isLoading = true,
                    error = ""
                )
            )
            runCatching {
                getFilmsUseCase(state.value.currentPage + 1, 10, listOf("id", "name", "year", "poster", "shortDescription", "rating"))
            }.onSuccess { films ->
                _state.emit(
                    _state.value.copy(
                        data = films,
                        isLoading = false,
                        error = "",
                        currentPage = state.value.currentPage + 1
                    )
                )
            }.onFailure { ex ->
                _state.emit(
                    _state.value.copy(
                        isLoading = false,
                        error = ex.toString(),
                        currentPage = state.value.currentPage + 1
                    )
                )
            }
        }
    }

    private fun onBackPageBtnClick() {

        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    isLoading = true,
                    error = ""
                )
            )
            runCatching {
                getFilmsUseCase(state.value.currentPage - 1, 10, listOf("id", "name", "year", "poster", "shortDescription", "rating"))
            }.onSuccess { films ->
                _state.emit(
                    _state.value.copy(
                        data = films,
                        isLoading = false,
                        error = "",
                        currentPage = state.value.currentPage - 1
                    )
                )
            }.onFailure { ex ->
                _state.emit(
                    _state.value.copy(
                        isLoading = false,
                        error = ex.toString(),
                        currentPage = state.value.currentPage - 1
                    )
                )
            }
        }
    }


    private fun onLaunch() {
        viewModelScope.launch {

            _state.emit(
                _state.value.copy(
                    isLoading = false,
                    username =
                    if (username == "Гость") "Гость" else username
                )
            )

            viewModelScope.launch {
                _state.emit(
                    _state.value.copy(
                        isLoading = true,
                        error = ""
                    )
                )
                runCatching {
                    getFilmsUseCase(state.value.currentPage, 10, listOf("id", "name", "year", "poster", "shortDescription", "rating"))
                }.onSuccess { films ->
                    _state.emit(
                        _state.value.copy(
                            data = films,
                            isLoading = false,
                            error = ""
                        )
                    )
                }.onFailure { ex ->
                    _state.emit(
                        _state.value.copy(
                            isLoading = false,
                            error = ex.toString()
                        )
                    )
                }
            }
        }
    }
}