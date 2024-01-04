package ru.itis.filmsandroidapp.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserCore(

    val firstName: String,
    val lastName: String,

    @PrimaryKey
    val username: String,

    val password: String,
)
