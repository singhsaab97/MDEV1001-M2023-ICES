package com.kanwarabhijitsingh.ice6

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
	@PrimaryKey(autoGenerate = true) var id: Int = 0,
	val firstName: String?,
	val lastName: String?,
	val username: String,
	val email: String?,
	val password: String
): Serializable
