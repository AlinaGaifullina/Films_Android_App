package ru.itis.filmsandroidapp.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.filmsandroidapp.core.db.dao.UserCoreDao
import ru.itis.filmsandroidapp.core.db.model.UserCore

private const val DATABASE_NAME = "app.db"

@Database(
    entities = [UserCore::class],
    version = 1
)

abstract class UserDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): UserDatabase = Room
            .databaseBuilder(context.applicationContext, UserDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun userCoreDao() : UserCoreDao

}