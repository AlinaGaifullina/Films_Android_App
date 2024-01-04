package ru.itis.filmsandroidapp.feature.profile.impl.repository

import ru.itis.filmsandroidapp.core.db.FavouritesDatabase
import ru.itis.filmsandroidapp.core.db.UserDatabase
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.core.network.FilmsApiService
import ru.itis.filmsandroidapp.core.network.model.mapFilmInfoEntity
import ru.itis.filmsandroidapp.feature.profile.api.mappers.toUserProfile
import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile
import ru.itis.filmsandroidapp.feature.profile.api.model.mapShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import javax.inject.Inject

internal class UserProfileRepositoryImpl @Inject constructor(
    private val userDb: UserDatabase,
    private val favouritesDb: FavouritesDatabase,
    private val remoteSource: FilmsApiService,
): UserProfileRepository {
    override suspend fun getUserByUsername(username: String): UserProfile {
        return userDb.userCoreDao().getUserByUsername(username).toUserProfile()
    }

    override suspend fun updateUser(user: UserProfile) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: UserProfile) {
        TODO("Not yet implemented")
    }

    override suspend fun getFilmById(filmId: Int): ShortFilmInfoModel {
        return remoteSource.getFilmById(filmId).mapFilmInfoEntity().mapShortFilmInfoModel()
    }

    override suspend fun getFavouritesByUsername(username: String): List<FavouritesCore> {
        return favouritesDb.favouritesCoreDao().getFavouritesByUsername(username)
    }

}