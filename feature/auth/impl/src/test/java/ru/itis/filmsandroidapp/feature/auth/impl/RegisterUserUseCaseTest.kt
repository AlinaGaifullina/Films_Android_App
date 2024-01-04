package ru.itis.filmsandroidapp.feature.auth.impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.filmsandroidapp.feature.auth.api.actions_results.RegisterResult
import ru.itis.filmsandroidapp.feature.auth.api.model.User
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.RegisterUserUseCase
import ru.itis.filmsandroidapp.feature.auth.impl.usecase.RegisterUserUseCaseImpl


class FakeUserRepository2(private val user: User?, private val exceptionOnCreate: Boolean = false) : UserRepository {
    override suspend fun createUser(user: User) {
        if (exceptionOnCreate) {
            throw Exception("Ошибка при регистрации пользователя")
        }
    }

    override suspend fun getUserByUsername(username: String): User? {
        return user
    }
}

class RegisterUserUseCaseTest {

    private lateinit var userRepository: FakeUserRepository2

    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Test
    fun testSuccessRegister() = runBlocking {
        val firstName = "John"
        val lastName = "Doe"
        val username = "john_doe"
        val password = "securePassword"

        userRepository = FakeUserRepository2(null)
        registerUserUseCase = RegisterUserUseCaseImpl(userRepository)

        val result = registerUserUseCase.invoke(firstName, lastName, username, password)

        assertEquals(RegisterResult.SuccessRegister(username), result)
    }

    @Test
    fun testFailRegisterUserExists() = runBlocking {
        val existingUser = User("Existing", "User", "existing_user", "existingPassword")
        val firstName = "John"
        val lastName = "Doe"
        val username = existingUser.username
        val password = "securePassword"

        userRepository = FakeUserRepository2(existingUser)
        registerUserUseCase = RegisterUserUseCaseImpl(userRepository)

        val result = registerUserUseCase.invoke(firstName, lastName, username, password)

        assertEquals(RegisterResult.FailRegister("Пользователь с таким именем уже существует"), result)
    }

    @Test
    fun testFailRegisterException() = runBlocking {
        val firstName = "John"
        val lastName = "Doe"
        val username = "john_doe"
        val password = "securePassword"

        val user = User(firstName, lastName, username, password)

        // Имитируем сценарий, когда репозиторий выбрасывает исключение
        userRepository = FakeUserRepository2(user, exceptionOnCreate = true)
        registerUserUseCase = RegisterUserUseCaseImpl(userRepository)

        val result = registerUserUseCase.invoke(firstName, lastName, username, password)

        assertEquals(RegisterResult.FailRegister("Ошибка при регистрации пользователя"), result)
    }

    // Добавьте дополнительные тесты по мере необходимости
}