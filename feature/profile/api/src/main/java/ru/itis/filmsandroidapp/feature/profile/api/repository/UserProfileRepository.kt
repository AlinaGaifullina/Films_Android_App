package ru.itis.filmsandroidapp.feature.profile.api.repository

import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile

interface UserProfileRepository {
    suspend fun getUserByUsername(username: String): UserProfile?
    suspend fun updateUser(user: UserProfile)
    suspend fun deleteUser(user: UserProfile)

    suspend fun getFilmById(filmId: Int) : ShortFilmInfoModel

    suspend fun getFavouritesByUsername(username: String) : List<FavouritesCore>
}