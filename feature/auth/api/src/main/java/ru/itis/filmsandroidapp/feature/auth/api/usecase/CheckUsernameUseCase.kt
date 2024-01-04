package ru.itis.filmsandroidapp.feature.auth.api.usecase

interface CheckUsernameUseCase {

    suspend operator fun invoke(username: String): Boolean

}