package ru.itis.filmsandroidapp.feature.auth.api.usecase

import ru.itis.filmsandroidapp.feature.auth.api.actions_results.LoginResult

interface LoginUserUseCase {

    suspend operator fun invoke(username: String, password: String): LoginResult
}