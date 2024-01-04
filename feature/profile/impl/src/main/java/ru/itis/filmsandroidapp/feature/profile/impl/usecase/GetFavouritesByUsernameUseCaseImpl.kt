package ru.itis.filmsandroidapp.feature.profile.impl.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFavouritesByUsernameUseCase

internal class GetFavouritesByUsernameUseCaseImpl (
    private val userProfileRepository: UserProfileRepository,
) : GetFavouritesByUsernameUseCase {

    override suspend fun invoke(username: String): List<FavouritesCore> {
        return userProfileRepository.getFavouritesByUsername(username)
    }
}