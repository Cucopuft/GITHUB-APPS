package com.example.may_githubapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class DbUser : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: DbUser? = null

        fun getDB(context: Context): DbUser {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): DbUser {
            return Room.databaseBuilder(context.applicationContext, DbUser::class.java, "Db_User").build()
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDAO
}
