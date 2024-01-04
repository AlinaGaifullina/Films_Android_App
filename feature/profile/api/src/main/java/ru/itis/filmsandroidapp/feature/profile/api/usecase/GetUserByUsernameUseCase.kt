package ru.itis.filmsandroidapp.feature.profile.api.usecase

import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile

interface GetUserByUsernameUseCase {

    suspend operator fun invoke(username: String): UserProfile?
}