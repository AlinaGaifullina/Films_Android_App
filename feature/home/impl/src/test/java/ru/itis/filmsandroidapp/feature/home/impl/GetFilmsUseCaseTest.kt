package ru.itis.filmsandroidapp.feature.home.impl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel
import ru.itis.filmsandroidapp.feature.home.api.repository.FilmsRepository
import ru.itis.filmsandroidapp.feature.home.impl.usecase.GetFilmsUseCaseImpl

class GetFilmsUseCaseTest {

    @Mock
    lateinit var filmsRepository: FilmsRepository

    private lateinit var getFilmsUseCase: GetFilmsUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFilmsUseCase = GetFilmsUseCaseImpl(filmsRepository)
    }

    @Test
    fun `get films successfully`() = runBlocking {
        val numberPage = 1
        val numberFilmsOnPage = 10
        val selectFields = listOf("title", "description")
        val testShortFilmResponse = ShortFilmResponseModel(
            1,
            10,
            100,
            listOf( ShortFilmInfoModel(
                123,
                "filmName",
                "2003",
                "shortDescription",
                "5",
                "url")
            )
        )

        `when`(filmsRepository.getFilms(numberPage, numberFilmsOnPage, selectFields)).thenReturn(testShortFilmResponse)

        val result = getFilmsUseCase.invoke(numberPage, numberFilmsOnPage, selectFields)

        val expectedShortFilmResponse = ShortFilmResponseModel(
            1,
            10,
            100,
            listOf( ShortFilmInfoModel(
                123,
                "filmName",
                "2003",
                "shortDescription",
                "5",
                "url")
            )
        )

        verify(filmsRepository).getFilms(numberPage, numberFilmsOnPage, selectFields)

        assertEquals(expectedShortFilmResponse, result)
    }
}