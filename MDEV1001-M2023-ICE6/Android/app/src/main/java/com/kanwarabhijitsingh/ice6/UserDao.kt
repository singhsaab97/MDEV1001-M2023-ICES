package com.kanwarabhijitsingh.ice6

import androidx.room.*

@Dao
interface UserDao {
	@Query("SELECT COUNT(*) FROM User WHERE username = :username OR password = :password")
	fun countUser(username: String, password: String): Int

	@Insert(onConflict = OnConflictStrategy.ABORT)
	fun addUser(user: User)
}
