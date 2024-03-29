package com.kanwarabhijitsingh.ice4

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
	abstract fun movieDao(): MovieDao
}
