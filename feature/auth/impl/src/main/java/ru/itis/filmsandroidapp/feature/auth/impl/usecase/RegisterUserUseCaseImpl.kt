package ru.itis.filmsandroidapp.feature.auth.impl.usecase

import ru.itis.filmsandroidapp.feature.auth.api.actions_results.RegisterResult
import ru.itis.filmsandroidapp.feature.auth.api.model.User
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.RegisterUserUseCase
import javax.inject.Inject

internal class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : RegisterUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        username: String,
        password: String
    ): RegisterResult {
        return try {
            val hashedPassword = password //TODO
            userRepository.createUser(User(firstName, lastName, username, hashedPassword))
            RegisterResult.SuccessRegister(username)
        } catch (e: Exception) {
            RegisterResult.FailRegister(e.message)
        }
    }
}