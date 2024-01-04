package ru.itis.filmsandroidapp.feature.profile.impl.presentation.profile

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
import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile
import ru.itis.filmsandroidapp.feature.profile.api.navigation.ProfileDestination.USERNAME_PARAM
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFavouritesByUsernameUseCase
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFilmByFilmIdUseCase
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetUserByUsernameUseCase
import javax.inject.Inject

@Immutable
data class ProfileState(
    val username: String = "",
    val user: UserProfile = UserProfile("", "", "", ""),
    val favouritesFilms: List<ShortFilmInfoModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
)

sealed interface ProfileEvent {
    object OnLaunch : ProfileEvent
    data class OnFilmCardClick(val filmId: Int) : ProfileEvent
    object OnBackBtnClick : ProfileEvent
}

sealed interface ProfileSideEffect {
    data class NavigateToFilmDetailsScreen(val filmId: Int) : ProfileSideEffect
    object NavigateBack : ProfileSideEffect
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val getFavouritesByUsernameUseCase: GetFavouritesByUsernameUseCase,
    private val getFilmByFilmIdUseCase: GetFilmByFilmIdUseCase,
) : ViewModel(), Navigator by navigator {

    private val username: String = savedStateHandle[USERNAME_PARAM] ?: "Гость"

    private val _state: MutableStateFlow<ProfileState> =
        MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _action = MutableSharedFlow<ProfileSideEffect?>()
    val action: SharedFlow<ProfileSideEffect?>
        get() = _action.asSharedFlow()

    fun event(profileEvent: ProfileEvent) {
        ViewModelProvider.NewInstanceFactory
        when (profileEvent) {
            ProfileEvent.OnLaunch -> onLaunch()
            is ProfileEvent.OnFilmCardClick -> onFilmCardClick(profileEvent.filmId)
            ProfileEvent.OnBackBtnClick -> onBackBtnClick()
        }
    }

    private fun onBackBtnClick(){
        viewModelScope.launch {
            _action.emit(ProfileSideEffect.NavigateBack)
        }
    }
    //
    private fun onLaunch() {

        viewModelScope.launch {
            val user = getUserByUsernameUseCase(username)
            val favourites = getFavouritesByUsernameUseCase(username)
            val favouritesFilms = mutableListOf<ShortFilmInfoModel>()

            runCatching {
                favourites.forEach {
                    favouritesFilms.add(getFilmByFilmIdUseCase(it.filmId))
                }
            }.onSuccess { films ->
                _state.emit(
                    _state.value.copy(
                        user = user ?: UserProfile("", "", "", ""),
                        favouritesFilms = favouritesFilms,
                        isLoading = false
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

    private fun onFilmCardClick(filmId: Int){
        viewModelScope.launch {
            _action.emit(ProfileSideEffect.NavigateToFilmDetailsScreen(filmId))
        }
    }
}