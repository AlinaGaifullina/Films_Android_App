package ru.itis.filmsandroidapp.feature.film_details.impl.presentation

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
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.core.navigation.Navigator
import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity
import ru.itis.filmsandroidapp.core.network.model.PosterInfoEntity
import ru.itis.filmsandroidapp.core.network.model.RatingInfoEntity
import ru.itis.filmsandroidapp.feature.film_details.api.navigation.FilmDetailsDestination.FILM_ID_PARAM
import ru.itis.filmsandroidapp.feature.film_details.api.navigation.FilmDetailsDestination.USERNAME_PARAM
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.AddFilmToFavouritesUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.DeleteFilmFromFavouritesUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFavouritesByUsernameUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFilmByIdUseCase
import javax.inject.Inject

@Immutable
data class FilmDetailsState(
    val filmId: Int = 0,
    val isFavouriteFilm: Boolean = false,
    val favourites: List<FavouritesCore> = emptyList(),
    val searchValue: String = "",
    val searchByName: Boolean = true,
    val isLoading: Boolean = true,
    val error: String = "",
    val data: FilmInfoEntity =
        FilmInfoEntity(
            0,
            "",
            "",
            PosterInfoEntity("", ""),
            "",
            "",
            RatingInfoEntity("", "", "", "", ""),
            emptyList()
        )
)

sealed interface FilmDetailsEvent {
    object OnLaunch : FilmDetailsEvent
    object OnFavouriteChange : FilmDetailsEvent
    object OnBackBtnClick : FilmDetailsEvent

}

sealed interface FilmDetailsSideEffect {
    object NavigateBack : FilmDetailsSideEffect
}

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val addFilmToFavouritesUseCase: AddFilmToFavouritesUseCase,
    private val deleteFilmFromFavouritesUseCase: DeleteFilmFromFavouritesUseCase,
    private val getFavouritesByUsernameUseCase: GetFavouritesByUsernameUseCase,
) : ViewModel(), Navigator by navigator {

    private val filmId: Int = savedStateHandle[FILM_ID_PARAM] ?: 0
    private val username: String = savedStateHandle[USERNAME_PARAM] ?: "Гость"


    private val _state: MutableStateFlow<FilmDetailsState> =
        MutableStateFlow(FilmDetailsState())
    val state: StateFlow<FilmDetailsState> = _state

    private val _action = MutableSharedFlow<FilmDetailsSideEffect?>()
    val action: SharedFlow<FilmDetailsSideEffect?>
        get() = _action.asSharedFlow()

    fun event(homeEvent: FilmDetailsEvent) {
        ViewModelProvider.NewInstanceFactory
        when (homeEvent) {
            FilmDetailsEvent.OnLaunch -> onLaunch()
            FilmDetailsEvent.OnFavouriteChange -> onFavouriteChange()
            FilmDetailsEvent.OnBackBtnClick -> onBackBtnClick()
        }
    }

    private fun onBackBtnClick(){
        viewModelScope.launch {
            _action.emit(FilmDetailsSideEffect.NavigateBack)
        }
    }

    private fun onFavouriteChange(){

        val favouritesFilmId = mutableListOf<Int>()
        viewModelScope.launch {
            val favourites = getFavouritesByUsernameUseCase(username)
            favourites.forEach {
                favouritesFilmId.add(it.filmId)
            }
            if(favouritesFilmId.contains(filmId)){
                favourites.forEach {
                    if(it.filmId == filmId){
                        deleteFilmFromFavouritesUseCase(it)
                    }
                }
                _state.emit(
                    _state.value.copy(
                        isFavouriteFilm = false,
                    )
                )
            } else {
                addFilmToFavouritesUseCase(username, filmId)
                _state.emit(
                    _state.value.copy(
                        isFavouriteFilm = true,
                        favourites = favourites
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
                    filmId = filmId
                )
            )

            viewModelScope.launch {
                _state.emit(
                    _state.value.copy(
                        isLoading = true,
                        error = ""
                    )
                )

                val favourites = mutableListOf<Int>()
                getFavouritesByUsernameUseCase(username).forEach {
                    favourites.add(it.filmId)
                }
                runCatching {
                    getFilmByIdUseCase(filmId.toInt())
                }.onSuccess { film ->
                    _state.emit(
                        _state.value.copy(
                            data = film,
                            isLoading = false,
                            isFavouriteFilm = favourites.contains(filmId),
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