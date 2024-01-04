package ru.itis.filmsandroidapp.feature.film_details.impl.repository

import ru.itis.filmsandroidapp.core.db.FavouritesDatabase
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.core.network.FilmsApiService
import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity
import ru.itis.filmsandroidapp.core.network.model.mapFilmInfoEntity
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import javax.inject.Inject

internal class FilmDetailsRepositoryImpl @Inject constructor(
    private val remoteSource: FilmsApiService,
    private val favouritesDb: FavouritesDatabase
): FilmDetailsRepository {
    override suspend fun getFilmById(filmId: Int): FilmInfoEntity {
        return remoteSource.getFilmById(filmId).mapFilmInfoEntity()
    }

    override suspend fun addFilmToFavourites(username: String, filmId: Int) : Boolean {
        return try {
            favouritesDb.favouritesCoreDao().insertFavourites( FavouritesCore(username = username, filmId = filmId))
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getFavouritesById(favouritesId: Int): FavouritesCore {
        return favouritesDb.favouritesCoreDao().getFavouritesById(favouritesId)
    }

    override suspend fun getFavouritesByUsername(username: String): List<FavouritesCore> {
        return favouritesDb.favouritesCoreDao().getFavouritesByUsername(username)
    }

    override suspend fun deleteFromFavourites(favourites: FavouritesCore) : Boolean {
        return try {
            favouritesDb.favouritesCoreDao().deleteFavourites(favourites)
            true
        } catch (e: Exception) {
            false
        }
    }
}
