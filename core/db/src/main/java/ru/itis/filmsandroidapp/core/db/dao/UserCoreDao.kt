package ru.itis.filmsandroidapp.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.filmsandroidapp.core.db.model.UserCore

@Dao
interface UserCoreDao{

    @Query("select * from users where username = :username")
    suspend fun getUserByUsername(username: String): UserCore

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserCore)

    @Delete
    suspend fun deleteUser(user: UserCore)

}