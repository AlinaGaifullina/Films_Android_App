package ru.itis.filmsandroidapp.feature.auth.api.repository

import ru.itis.filmsandroidapp.feature.auth.api.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getUserByUsername(username: String): User?
}