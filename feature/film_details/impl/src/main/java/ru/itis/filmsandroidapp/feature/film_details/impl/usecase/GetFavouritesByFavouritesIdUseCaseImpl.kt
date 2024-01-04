package ru.itis.filmsandroidapp.feature.film_details.impl.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFavouritesByFavouritesIdUseCase

internal class GetFavouritesByFavouritesIdUseCaseImpl(
    private val filmsDetailsRepository: FilmDetailsRepository,
) : GetFavouritesByFavouritesIdUseCase {

    override suspend fun invoke(favouritesId: Int): FavouritesCore {
        return filmsDetailsRepository.getFavouritesById(favouritesId)
    }
}