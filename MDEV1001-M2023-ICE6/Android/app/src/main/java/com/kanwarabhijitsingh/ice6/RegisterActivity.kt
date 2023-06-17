package com.kanwarabhijitsingh.ice6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)
		setup()
	}

	private fun setup() {
		val registerButton = findViewById<Button>(R.id.registerButton)
		registerButton.setOnClickListener {
			tryAddingNewUser()
		}
		val firstName = findViewById<EditText>(R.id.firstName)
		firstName.requestFocus()
	}

	private fun tryAddingNewUser() {
		val firstName = findViewById<EditText>(R.id.firstName).text.toString()
		val lastName = findViewById<EditText>(R.id.lastName).text.toString()
		val username = findViewById<EditText>(R.id.username).text.toString()
		val emailId = findViewById<EditText>(R.id.emailId).text.toString()
		val password = findViewById<EditText>(R.id.password).text.toString()
		val confirmPassword = findViewById<EditText>(R.id.confirmPassword).text.toString()
		// Check if required fields are empty
		if (username.isEmpty()) {
			showAlert(null, "Username is required")
		} else if (password.isEmpty()) {
			showAlert(null, "Password is required")
		} else if (password != confirmPassword) {
			showAlert(null, "Entered passwords do not match")
		} else {
			// Add new user
			val user = User(
				firstName = firstName,
				lastName = lastName,
				username = username,
				email = emailId,
				password = password
			)
			val dao = UserDatabase(this@RegisterActivity).userDao()
			if (dao.countUser(username, password) > 0) {
				// Trying to create a duplicate user
				showAlert("Registration failed", "This user already exists in the database. Please try again")
			} else {
				dao.addUser(user)
				finish()
			}
		}
	}

	private fun showAlert(title: String?, message: String) {
		val builder = AlertDialog.Builder(this@RegisterActivity)
		builder.setTitle(title)
		builder.setMessage(message)
		builder.setPositiveButton("Ok") { alert, _ ->
			alert.dismiss()
		}
		builder.create().show()
	}

}
