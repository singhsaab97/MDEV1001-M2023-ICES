package com.kanwarabhijitsingh.ice6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		setup()
	}

	private fun setup() {
		val loginButton = findViewById<Button>(R.id.loginButton)
		loginButton.setOnClickListener {
			validateCredentials()
		}
		val registerButton = findViewById<Button>(R.id.registerButton)
		registerButton.setOnClickListener {
			// Show registration screen
			val intent = Intent(this, RegisterActivity::class.java)
			startActivity(intent)
		}
	}

	private fun validateCredentials() {
		val username = findViewById<EditText>(R.id.username).text.toString()
		val password = findViewById<EditText>(R.id.password).text.toString()
		if (username.isEmpty()) {
			showAlert(null, "Username is required")
		} else if (password.isEmpty()) {
			showAlert(null, "Password is required")
		} else {
			// Check if this user exists
			val database = UserDatabase(this)
			val isValidUser = database.userDao().countUser(username, password) > 0
			if (isValidUser) {
				// Show movies screen
				val intent = Intent(this, MoviesActivity::class.java)
				startActivity(intent)
			} else {
				showAlert("Invalid login credentials", "Please check the details and try again")
			}
		}
	}

	private fun showAlert(title: String?, message: String) {
		val builder = AlertDialog.Builder(this@LoginActivity)
		builder.setTitle(title)
		builder.setMessage(message)
		builder.setPositiveButton("Ok") { alert, _ ->
			alert.dismiss()
		}
		builder.create().show()
	}

}
