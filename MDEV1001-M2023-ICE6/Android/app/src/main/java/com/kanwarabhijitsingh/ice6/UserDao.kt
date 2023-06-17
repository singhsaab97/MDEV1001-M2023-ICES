package com.kanwarabhijitsingh.ice6

import androidx.room.*

@Dao
interface UserDao {
	@Query("SELECT * FROM User")
	fun getAllUsers(): List<User>

	@Query("SELECT COUNT(*) FROM User WHERE username = :username AND password = :password")
	fun countUser(username: String, password: String): Int

	@Insert(onConflict = OnConflictStrategy.ABORT)
	fun addUser(user: User)
}
