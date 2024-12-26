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
        val authToken = "your_auth_token_here" // Replace with actual token retrieval logic

        // Make the request to fetch user details
        fetchUserData(authToken)
    }

    private fun fetchUserData(authToken: String) {
        // Adding the Authorization header to the request
        val headers = mapOf("Authorization" to "Bearer $authToken")

        RetrofitInstance.api.getUserDetails(headers).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val users = data?.get("data") as? List<Map<String, Any>>

                    if (users != null && users.isNotEmpty()) {
                        // Assuming we want to show the first user's info
                        val user = users[0]
                        val username = user["username"] as? String
                        val email = user["email"] as? String

                        if (username != null && email != null) {
                            // Display user data
                            usernameTextView.text = "Username: $username"
                            emailTextView.text = "Email: $email"
                        } else {
                            // Handle missing username or email
                            errorMessage.text = "Error: Missing username or email."
                            errorMessage.visibility = View.VISIBLE
                        }
                    } else {
                        // Handle empty user list or no users
                        errorMessage.text = "Error: No users found."
                        errorMessage.visibility = View.VISIBLE
                    }
                } else {
                    // Handle error response
                    errorMessage.text = "Error: ${response.message()}"
                    errorMessage.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                // Handle failure case
                errorMessage.text = "Error: ${t.message}"
                errorMessage.visibility = View.VISIBLE
            }
        })
    }
}
