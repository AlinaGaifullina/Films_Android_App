package ru.itis.filmsandroidapp.feature.auth.impl.repository

import ru.itis.filmsandroidapp.core.db.UserDatabase
import ru.itis.filmsandroidapp.feature.auth.api.model.User
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.impl.mapper.toUser
import ru.itis.filmsandroidapp.feature.auth.impl.mapper.toUserCore
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val db: UserDatabase,
): UserRepository {

    override suspend fun createUser(user: User) {
        return db.userCoreDao().insertUser(user.toUserCore())
    }

    override suspend fun getUserByUsername(username: String): User {
        return db.userCoreDao().getUserByUsername(username).toUser()
    }
}