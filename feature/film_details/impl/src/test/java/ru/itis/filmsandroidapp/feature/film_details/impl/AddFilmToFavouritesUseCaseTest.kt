package ru.itis.filmsandroidapp.feature.film_details.impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.AddFilmToFavouritesUseCaseImpl



class AddFilmToFavouritesUseCaseTest {

    private val filmsDetailsRepository = mock<FilmDetailsRepository>()

    private val addFilmToFavouritesUseCase = AddFilmToFavouritesUseCaseImpl(filmsDetailsRepository)

    @Test
    fun `film added to favorites successfully`() = runBlocking {
        val username = "testUser"
        val filmId = 123

        whenever(filmsDetailsRepository.addFilmToFavourites(username, filmId)).thenReturn(true)

        val actual = addFilmToFavouritesUseCase.invoke(username, filmId)

        verify(filmsDetailsRepository).addFilmToFavourites(username, filmId)

        assertTrue("Film should be added to favorites", actual)
    }
}