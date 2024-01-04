package ru.itis.filmsandroidapp.core.db.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.itis.filmsandroidapp.core.db.FavouritesDatabase
import ru.itis.filmsandroidapp.core.db.UserDatabase
import ru.itis.filmsandroidapp.core.db.dao.FavouritesCoreDao

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    fun provideAppDatabase(context: Context): UserDatabase {
        return UserDatabase.get(context)
    }

    @Provides
    fun provideFavouritesDatabase(context: Context): FavouritesDatabase {
        return FavouritesDatabase.get(context)
    }

    @Provides
    fun provideFavouritesDao(favouritesDatabase: FavouritesDatabase): FavouritesCoreDao {
        return favouritesDatabase.favouritesCoreDao()
    }
}