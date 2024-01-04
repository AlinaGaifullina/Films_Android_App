package ru.itis.filmsandroidapp.feature.profile.impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetFavouritesByUsernameUseCaseImpl

class GetFavouritesByUsernameUseCaseTest {

    @Mock
    lateinit var userProfileRepository: UserProfileRepository

    private lateinit var getFavouritesByUsernameUseCase: GetFavouritesByUsernameUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFavouritesByUsernameUseCase = GetFavouritesByUsernameUseCaseImpl(userProfileRepository)
    }

    @Test
    fun `get favorites by username successfully`() = runBlocking {
        val username = "testUser"
        val testFavouritesList = listOf(FavouritesCore(123, "username", 456))

        `when`(userProfileRepository.getFavouritesByUsername(username)).thenReturn(testFavouritesList)

        val result = getFavouritesByUsernameUseCase.invoke(username)

        val expectedFavouritesList = listOf(FavouritesCore(123, "username", 456))

        verify(userProfileRepository).getFavouritesByUsername(username)

        assertEquals(expectedFavouritesList, result)
    }
}