package ru.itis.filmsandroidapp.feature.film_details.api.usecase

interface AddFilmToFavouritesUseCase {

    suspend operator fun invoke(username: String, filmId: Int) : Boolean
}