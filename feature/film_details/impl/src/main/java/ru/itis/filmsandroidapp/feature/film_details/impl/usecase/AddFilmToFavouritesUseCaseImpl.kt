package ru.itis.filmsandroidapp.feature.film_details.impl.usecase

import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.AddFilmToFavouritesUseCase

internal class AddFilmToFavouritesUseCaseImpl (
    private val filmsDetailsRepository: FilmDetailsRepository,
) : AddFilmToFavouritesUseCase {
    override suspend fun invoke(username: String, filmId: Int) : Boolean {
        return try {
            filmsDetailsRepository.addFilmToFavourites(username, filmId)
            true
        } catch (e: Exception){
            false
        }
    }
}