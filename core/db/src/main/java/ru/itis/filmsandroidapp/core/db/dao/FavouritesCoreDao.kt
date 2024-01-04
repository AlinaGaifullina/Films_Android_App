package ru.itis.filmsandroidapp.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.filmsandroidapp.core.db.model.FavouritesCore

@Dao
interface FavouritesCoreDao {

    @Query("select * from favourites where username = :username")
    suspend fun getFavouritesByUsername(username: String): List<FavouritesCore>

    @Query("select * from favourites where favouritesId = :favouritesId")
    suspend fun getFavouritesById(favouritesId: Int): FavouritesCore

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourites(favourites: FavouritesCore)

    @Delete
    suspend fun deleteFavourites(favourites: FavouritesCore)
}