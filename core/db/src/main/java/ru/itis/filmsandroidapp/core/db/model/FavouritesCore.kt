package ru.itis.filmsandroidapp.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")

data class FavouritesCore(

    @PrimaryKey(autoGenerate = true)
    val favouritesId: Int = 0,

    val username: String,
    val filmId: Int
)