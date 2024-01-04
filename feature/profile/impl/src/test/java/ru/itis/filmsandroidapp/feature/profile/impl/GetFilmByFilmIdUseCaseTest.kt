package ru.itis.filmsandroidapp.feature.profile.impl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetFilmByFilmIdUseCaseImpl

class GetFilmByFilmIdUseCaseTest {

    @Mock
    lateinit var userProfileRepository: UserProfileRepository

    private lateinit var getFilmByFilmIdUseCase: GetFilmByFilmIdUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFilmByFilmIdUseCase = GetFilmByFilmIdUseCaseImpl(userProfileRepository)
    }

    @Test
    fun `get film by film ID successfully`() = runBlocking {
        val filmId = 1
        val testShortFilmInfoModel = ShortFilmInfoModel(
            123,
            "filmName",
            "2003",
            "shortDescription",
            "5",
            "url"
        )

        // Устанавливаем поведение для фейкового репозитория
        `when`(userProfileRepository.getFilmById(filmId)).thenReturn(testShortFilmInfoModel)

        // Вызываем метод use case
        val result = getFilmByFilmIdUseCase.invoke(filmId)

        val expectedShortFilmInfoModel = ShortFilmInfoModel(
            123,
            "filmName",
            "2003",
            "shortDescription",
            "5",
            "url"
        )

        // Проверяем, что метод был вызван в репозитории с нужными аргументами
        verify(userProfileRepository).getFilmById(filmId)

        // Проверяем, что результат совпадает с ожидаемым
        assertEquals(expectedShortFilmInfoModel, result)
    }

}