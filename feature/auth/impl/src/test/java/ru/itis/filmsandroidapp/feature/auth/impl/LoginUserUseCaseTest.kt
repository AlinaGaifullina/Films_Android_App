package ru.itis.filmsandroidapp.feature.auth.impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.filmsandroidapp.feature.auth.api.actions_results.LoginResult
import ru.itis.filmsandroidapp.feature.auth.api.model.User
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.LoginUserUseCase
import ru.itis.filmsandroidapp.feature.auth.impl.usecase.LoginUserUseCaseImpl


class FakeUserRepository1(private val user: User?) : UserRepository {
    override suspend fun createUser(user: User) {
        // не нужно
    }

    override suspend fun getUserByUsername(username: String): User? {
        return user
    }
}
class LoginUserUseCaseTest {

    private lateinit var userRepository: FakeUserRepository1

    private lateinit var loginUserUseCase: LoginUserUseCase

    @Test
    fun testSuccessLogin() = runBlocking {
        val username = "testUsername"
        val firstName = "testFirstName"
        val lastName = "testLastName"
        val password = "testPassword"
        val user = User(firstName, lastName, username, password)

        userRepository = FakeUserRepository1(user)
        loginUserUseCase = LoginUserUseCaseImpl(userRepository)

        val result = loginUserUseCase.invoke(username, password)

        assertEquals(LoginResult.SuccessLogin(username), result)
    }

    @Test
    fun testFailLoginIncorrectPassword() = runBlocking {
        val username = "testUser"
        val firstName = "testFirstName"
        val lastName = "testLastName"
        val password = "testPassword"
        val incorrectPassword = "incorrectPassword"
        val user = User(firstName, lastName, username, password)

        userRepository = FakeUserRepository1(user)
        loginUserUseCase = LoginUserUseCaseImpl(userRepository)

        val result = loginUserUseCase.invoke(username, incorrectPassword)

        assertEquals(LoginResult.FailLogin("Неправильный пароль"), result)
    }

//    @Test
//    fun testFailLoginUserNotFound() = runBlocking {
//        val username = "nonexistentUser"
//        val password = "testPassword"
//
//        userRepository = FakeUserRepository(null)
//        loginUserUseCase = LoginUserUseCaseImpl(userRepository)
//
//        val result = loginUserUseCase.invoke(username, password)
//
//        assertEquals(LoginResult.FailLogin("Пользователь не найден"), result)
//    }

}