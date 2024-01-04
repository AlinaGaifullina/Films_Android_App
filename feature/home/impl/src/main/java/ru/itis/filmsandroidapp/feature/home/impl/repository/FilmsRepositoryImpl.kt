package ru.itis.filmsandroidapp.feature.home.impl.repository

import ru.itis.filmsandroidapp.core.network.FilmsApiService
import ru.itis.filmsandroidapp.core.network.model.mapFilmResponseEntity
import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel
import ru.itis.filmsandroidapp.feature.home.api.model.mapShortFilmResponseModel
import ru.itis.filmsandroidapp.feature.home.api.repository.FilmsRepository
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val remoteSource: FilmsApiService,
): FilmsRepository {

    override suspend fun getFilms(
        numberPage: Int,
        numberFilmsOnPage: Int,
        selectFields: List<String>
    ): ShortFilmResponseModel {
        return (remoteSource.getAllFilms(numberPage, numberFilmsOnPage, selectFields)).mapFilmResponseEntity().mapShortFilmResponseModel()
    }
}
