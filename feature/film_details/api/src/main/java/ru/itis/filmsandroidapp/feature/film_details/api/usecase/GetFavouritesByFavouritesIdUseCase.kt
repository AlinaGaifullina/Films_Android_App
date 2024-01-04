package ru.itis.filmsandroidapp.feature.film_details.api.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

interface GetFavouritesByFavouritesIdUseCase {

    suspend operator fun invoke(favouritesId: Int): FavouritesCore
}