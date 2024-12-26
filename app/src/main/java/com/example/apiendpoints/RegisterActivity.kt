package com.example.apiendpoints

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvResult = findViewById(R.id.tvResult)

        // Set the click listener for the register button
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Check if the inputs are not empty
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Call the registerUser function
                registerUser(username, email, password)
            } else {
                // Show error message if fields are empty
                tvResult.text = "Please fill in all fields."
                tvResult.visibility = View.VISIBLE
            }
        }
    }

    // Method to send registration request to the backend
    private fun registerUser(username: String, email: String, password: String) {
        // Create a map with registration details
        val registerData = mapOf("username" to username, "email" to email, "password" to password)

        // Send registration request using Retrofit
        RetrofitInstance.api.registerUser(registerData).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    tvResult.text = "Registration Successful!"
                } else {
                    // Handle failed registration, show error message
                    tvResult.text = "Registration Failed: ${response.errorBody()?.string()}"
                }
                tvResult.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                // Handle failure (e.g., network error)
                tvResult.text = "Registration Failed: ${t.message}"
                tvResult.visibility = View.VISIBLE
            }
        })
    }
}
