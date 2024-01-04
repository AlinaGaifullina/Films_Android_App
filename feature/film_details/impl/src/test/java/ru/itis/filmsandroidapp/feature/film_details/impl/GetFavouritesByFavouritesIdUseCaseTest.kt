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
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.GetFavouritesByFavouritesIdUseCaseImpl

class GetFavouritesByFavouritesIdUseCaseTest {


    @Mock
    lateinit var filmsDetailsRepository: FilmDetailsRepository

    private lateinit var getFavouritesByFavouritesIdUseCase: GetFavouritesByFavouritesIdUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFavouritesByFavouritesIdUseCase = GetFavouritesByFavouritesIdUseCaseImpl(filmsDetailsRepository)
    }

    @Test
    fun `get favorites by favoritesId successfully`() = runBlocking {
        val favouritesId = 123
        val testFavouritesCore = FavouritesCore(123, "testUsername", 456)

        `when`(filmsDetailsRepository.getFavouritesById(favouritesId)).thenReturn(testFavouritesCore)

        val result = getFavouritesByFavouritesIdUseCase.invoke(favouritesId)

        verify(filmsDetailsRepository).getFavouritesById(favouritesId)

        val expectedFavouritesCore = FavouritesCore(123, "testUsername", 456)

        assertEquals(expectedFavouritesCore, result)
    }
}