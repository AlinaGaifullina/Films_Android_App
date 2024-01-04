package ru.itis.filmsandroidapp.feature.film_details.impl.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.DeleteFilmFromFavouritesUseCase

internal class DeleteFilmFromFavouritesUseCaseImpl(
    private val filmsDetailsRepository: FilmDetailsRepository,
) : DeleteFilmFromFavouritesUseCase {

    override suspend fun invoke(favourites: FavouritesCore) : Boolean {
        return try {
            filmsDetailsRepository.deleteFromFavourites(favourites)
        } catch (e: Exception) {
            false
        }
    }
}