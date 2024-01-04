package ru.itis.filmsandroidapp.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.filmsandroidapp.core.db.dao.FavouritesCoreDao
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

private const val DATABASE_NAME = "favourites.db"

@Database(
    entities = [FavouritesCore::class],
    version = 1
)

abstract class FavouritesDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): FavouritesDatabase = Room
            .databaseBuilder(context.applicationContext, FavouritesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun favouritesCoreDao() : FavouritesCoreDao

}