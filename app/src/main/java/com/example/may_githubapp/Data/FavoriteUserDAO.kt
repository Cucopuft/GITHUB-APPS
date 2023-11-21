package com.example.may_githubapp.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDAO {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM Favorite_User")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT (*) FROM Favorite_User WHERE Favorite_User.id= :id")
    suspend fun cekUser(id:Int): Int

    @Query("DELETE FROM Favorite_User WHERE Favorite_User.id= :id")
    suspend fun removeFavorite(id:Int): Int
}