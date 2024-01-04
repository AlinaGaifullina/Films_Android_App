package ru.itis.filmsandroidapp.feature.profile.api.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

interface GetFavouritesByUsernameUseCase {

    suspend operator fun invoke(username: String): List<FavouritesCore>
}