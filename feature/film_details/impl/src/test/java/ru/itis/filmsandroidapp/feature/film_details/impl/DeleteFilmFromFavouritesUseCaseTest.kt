package ru.itis.filmsandroidapp.feature.film_details.impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.DeleteFilmFromFavouritesUseCaseImpl

class DeleteFilmFromFavouritesUseCaseTest {

    @Mock
    lateinit var filmsDetailsRepository: FilmDetailsRepository

    private lateinit var deleteFilmFromFavouritesUseCase: DeleteFilmFromFavouritesUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteFilmFromFavouritesUseCase = DeleteFilmFromFavouritesUseCaseImpl(filmsDetailsRepository)
    }

    @Test
    fun `delete film from favorites successfully`() = runBlocking {
        val favourites = FavouritesCore(0, "testUsername", 0)

        `when`(filmsDetailsRepository.deleteFromFavourites(favourites)).thenReturn(true)

        val actual = deleteFilmFromFavouritesUseCase.invoke(favourites)

        verify(filmsDetailsRepository).deleteFromFavourites(favourites)

        Assert.assertTrue("delete film from favorites successfully", actual)
    }

    @Test
    fun `delete film from favorites failed`() = runBlocking {
        val favourites = FavouritesCore(0, "testUsername", 0)

        `when`(filmsDetailsRepository.deleteFromFavourites(favourites)).thenReturn(false)

        val actual = deleteFilmFromFavouritesUseCase.invoke(favourites)

        verify(filmsDetailsRepository).deleteFromFavourites(favourites)

        Assert.assertTrue("delete film from favorites failed", !actual)
    }
}