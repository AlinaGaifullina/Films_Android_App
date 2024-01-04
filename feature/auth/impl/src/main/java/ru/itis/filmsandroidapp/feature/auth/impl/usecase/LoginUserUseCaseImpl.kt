package ru.itis.filmsandroidapp.feature.auth.impl.usecase

import ru.itis.filmsandroidapp.feature.auth.api.actions_results.LoginResult
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.LoginUserUseCase

internal class LoginUserUseCaseImpl(
    private val userRepository: UserRepository,
) : LoginUserUseCase {

    override suspend fun invoke(username: String, password: String): LoginResult {
        return try {

            val user = userRepository.getUserByUsername(username)
            if(password == user?.password){
                LoginResult.SuccessLogin(username)
            } else LoginResult.FailLogin("Неправильный пароль")
        } catch (e: java.lang.Exception) {
            LoginResult.FailLogin("Пользователь не найден")
        }
    }
}
