package ru.itis.filmsandroidapp.feature.film_details.impl.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFavouritesByUsernameUseCase

internal class GetFavouritesByUsernameUseCaseImpl(
    private val filmsDetailsRepository: FilmDetailsRepository,
) : GetFavouritesByUsernameUseCase {

    override suspend fun invoke(username: String): List<FavouritesCore> {
        return filmsDetailsRepository.getFavouritesByUsername(username)
    }
}