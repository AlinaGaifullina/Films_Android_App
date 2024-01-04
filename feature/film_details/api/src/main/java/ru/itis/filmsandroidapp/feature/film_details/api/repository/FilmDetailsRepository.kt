package ru.itis.filmsandroidapp.feature.film_details.api.repository

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity

interface FilmDetailsRepository {

    suspend fun getFilmById(filmId: Int) : FilmInfoEntity

    suspend fun addFilmToFavourites(username: String, filmId: Int) : Boolean

    suspend fun getFavouritesById(favouritesId: Int) : FavouritesCore

    suspend fun getFavouritesByUsername(username: String) : List<FavouritesCore>

    suspend fun deleteFromFavourites(favourites: FavouritesCore) : Boolean
}