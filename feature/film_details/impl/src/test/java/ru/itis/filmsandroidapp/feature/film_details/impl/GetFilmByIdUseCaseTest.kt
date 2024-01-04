package ru.itis.filmsandroidapp.feature.film_details.impl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity
import ru.itis.filmsandroidapp.core.network.model.PosterInfoEntity
import ru.itis.filmsandroidapp.core.network.model.RatingInfoEntity
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.GetFilmByIdUseCaseImpl

class GetFilmByIdUseCaseTest {


    @Mock
    lateinit var filmsDetailsRepository: FilmDetailsRepository

    private lateinit var getFilmByIdUseCase: GetFilmByIdUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFilmByIdUseCase = GetFilmByIdUseCaseImpl(filmsDetailsRepository)
    }

    @Test
    fun `get film by ID successfully`() = runBlocking {
        val filmId = 1
        val testFilmInfoEntity = FilmInfoEntity(
            12,
            "filmName",
            "2003",
            PosterInfoEntity("url", "url"),
            "shortDescription",
            "Description",
            RatingInfoEntity("5", "5", "5", "5","5"),
            emptyList()
        )

        // Устанавливаем поведение для фейкового репозитория
        `when`(filmsDetailsRepository.getFilmById(filmId)).thenReturn(testFilmInfoEntity)

        // Вызываем метод use case
        val result = getFilmByIdUseCase.invoke(filmId)

        val expectedFilmInfoEntity = FilmInfoEntity(
            12,
            "filmName",
            "2003",
            PosterInfoEntity("url", "url"),
            "shortDescription",
            "Description",
            RatingInfoEntity("5", "5", "5", "5","5"),
            emptyList()
        )

        // Проверяем, что метод был вызван в репозитории с нужным аргументом
        verify(filmsDetailsRepository).getFilmById(filmId)

        // Проверяем, что результат совпадает с ожидаемым
        assertEquals(expectedFilmInfoEntity, result)
    }
}