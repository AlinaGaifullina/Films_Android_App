package ru.itis.filmsandroidapp.feature.auth.impl.usecase

import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.CheckUsernameUseCase

internal class CheckUsernameUseCaseImpl (
    private val userRepository: UserRepository,
) : CheckUsernameUseCase {

    override suspend fun invoke(username: String): Boolean {
        return try {
            userRepository.getUserByUsername(username)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }
}