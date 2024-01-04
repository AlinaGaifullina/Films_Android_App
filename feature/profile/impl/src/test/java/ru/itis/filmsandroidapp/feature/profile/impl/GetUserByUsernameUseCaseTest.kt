package ru.itis.filmsandroidapp.feature.profile.impl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetUserByUsernameUseCaseImpl

class GetUserByUsernameUseCaseTest {


    @Mock
    lateinit var userProfileRepository: UserProfileRepository

    private lateinit var getUserByUsernameUseCase: GetUserByUsernameUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserByUsernameUseCase = GetUserByUsernameUseCaseImpl(userProfileRepository)
    }

    @Test
    fun `get user by username successfully`() = runBlocking {
        val username = "testUser"
        val testUserProfile = UserProfile(
            "firstName",
            "lastName", "testUser",
            "description"
        )

        `when`(userProfileRepository.getUserByUsername(username)).thenReturn(testUserProfile)

        val result = getUserByUsernameUseCase.invoke(username)

        val expectedUserProfile = UserProfile(
            "firstName",
            "lastName", "testUser",
            "description"
        )

        verify(userProfileRepository).getUserByUsername(username)

        assertEquals(expectedUserProfile, result)
    }

    @Test
    fun `get user by username handles exception`() = runBlocking {
        val username = "testUser"

        `when`(userProfileRepository.getUserByUsername(username)).thenThrow(RuntimeException("Error"))

        val result = getUserByUsernameUseCase.invoke(username)

        verify(userProfileRepository).getUserByUsername(username)

        assertEquals(UserProfile("", "", "", ""), result)
    }
}