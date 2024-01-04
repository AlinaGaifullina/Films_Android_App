package ru.itis.filmsandroidapp.feature.film_details.impl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.GetFavouritesByUsernameUseCaseImpl

class GetFavouritesByUsernameUseCaseTest {

    @Mock
    lateinit var filmsDetailsRepository: FilmDetailsRepository

    private lateinit var getFavouritesByUsernameUseCase: GetFavouritesByUsernameUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFavouritesByUsernameUseCase = GetFavouritesByUsernameUseCaseImpl(filmsDetailsRepository)
    }

    @Test
    fun `get favorites by username successfully`() = runBlocking {
        val username = "testUser"
        val expectedFavouritesList = listOf(
            FavouritesCore(123, "testUser", 456),
            FavouritesCore(789, "testUser", 123)
        )

        `when`(filmsDetailsRepository.getFavouritesByUsername(username)).thenReturn(expectedFavouritesList)

        val result = getFavouritesByUsernameUseCase.invoke(username)

        verify(filmsDetailsRepository).getFavouritesByUsername(username)

        assertEquals(expectedFavouritesList, result)
    }

    @Test
    fun `get favorites by username with empty result`() = runBlocking {
        val username = "nonExistentUser"

        `when`(filmsDetailsRepository.getFavouritesByUsername(username)).thenReturn(emptyList())

        val result = getFavouritesByUsernameUseCase.invoke(username)

        verify(filmsDetailsRepository).getFavouritesByUsername(username)

        assertEquals(emptyList<FavouritesCore>(), result)
    }

}