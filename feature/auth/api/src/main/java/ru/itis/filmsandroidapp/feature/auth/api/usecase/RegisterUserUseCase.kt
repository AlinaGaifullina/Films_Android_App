package ru.itis.filmsandroidapp.feature.auth.api.usecase

import ru.itis.filmsandroidapp.feature.auth.api.actions_results.RegisterResult

interface RegisterUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        username: String,
        password: String
    ): RegisterResult
}