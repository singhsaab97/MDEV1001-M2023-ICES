package com.kanwarabhijitsingh.ice6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
	abstract fun movieDao(): MovieDao

	companion object {
		@Volatile
		private var instance: MovieDatabase? = null
		private val LOCK = Any()

		operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
			instance ?: Room.databaseBuilder(
				context.applicationContext,
				MovieDatabase::class.java,
				"movie_database"
			).allowMainThreadQueries().build()
		}
	}
}
