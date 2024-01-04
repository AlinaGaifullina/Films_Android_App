package ru.itis.filmsandroidapp.feature.film_details.api.usecase

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

interface DeleteFilmFromFavouritesUseCase {

    suspend operator fun invoke(favourites: FavouritesCore) : Boolean
}