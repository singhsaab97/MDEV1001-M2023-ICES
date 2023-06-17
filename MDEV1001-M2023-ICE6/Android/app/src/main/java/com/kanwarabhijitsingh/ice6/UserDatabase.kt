package com.kanwarabhijitsingh.ice6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao

	companion object {
		@Volatile
		private var instance: UserDatabase? = null
		private val LOCK = Any()

		operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
			instance ?: Room.databaseBuilder(
				context.applicationContext,
				UserDatabase::class.java,
				"user_database"
			).allowMainThreadQueries().build()
		}
	}
}
