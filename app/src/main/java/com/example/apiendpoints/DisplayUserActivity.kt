package com.example.apiendpoints

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayUserActivity : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user)

        usernameTextView = findViewById(R.id.usernameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        errorMessage = findViewById(R.id.errorMessage)

        // Get the auth token from SharedPreferences or wherever it's stored
        val authToken = "your_auth_token_here"

        // Make the request to fetch user details
        fetchUserData(authToken)
    }

    private fun fetchUserData(authToken: String) {
        val headers = mapOf("Authorization" to "Bearer $authToken")

        RetrofitInstance.api.getUserData(headers).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val username = data?.get("username") as? String
                    val email = data?.get("email") as? String

                    if (username != null && email != null) {
                        // Display user data
                        usernameTextView.text = "Username: $username"
                        emailTextView.text = "Email: $email"
                    } else {
                        // Handle case where the username or email is null
                        errorMessage.text = "Error: Missing username or email."
                        errorMessage.visibility = View.VISIBLE
                    }
                } else {
                    // Handle response error
                    errorMessage.text = "Error: ${response.message()}"
                    errorMessage.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                errorMessage.text = "Error: ${t.message}"
                errorMessage.visibility = View.VISIBLE
            }
        })
    }
}
