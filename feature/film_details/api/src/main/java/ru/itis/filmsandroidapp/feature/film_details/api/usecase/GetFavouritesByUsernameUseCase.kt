package ru.itis.filmsandroidapp.feature.film_details.api.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

interface GetFavouritesByUsernameUseCase {

    suspend operator fun invoke(username: String): List<FavouritesCore>

}