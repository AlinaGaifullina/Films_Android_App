package ru.itis.filmsandroidapp.feature.home.api.repository

import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel

interface FilmsRepository {

    suspend fun getFilms(
        numberPage: Int,
        numberFilmsOnPage: Int,
        selectFields: List<String>
    ) : ShortFilmResponseModel
}