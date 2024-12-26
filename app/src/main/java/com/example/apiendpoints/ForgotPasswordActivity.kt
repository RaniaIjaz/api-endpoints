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

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_activity)

        // Initialize views
        etEmail = findViewById(R.id.etEmail)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvResult = findViewById(R.id.tvResult)

        // Set click listener for submit button
        btnSubmit.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isNotEmpty()) {
                sendForgotPasswordRequest(email)
            } else {
                tvResult.text = "Please enter an email."
                tvResult.visibility = View.VISIBLE
            }
        }
    }

    // Method to call the forgot password API
    private fun sendForgotPasswordRequest(email: String) {
        val request = ForgotPasswordRequest(email)

        // Send request using Retrofit
        RetrofitInstance.api.forgotPassword(request).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    val message = response.body()?.get("message")
                    if (message != null) {
                        tvResult.text = message
                    } else {
                        tvResult.text = "Error: Unexpected response from server."
                    }
                } else {
                    // Handle specific error responses from the server
                    when (response.code()) {
                        404 -> tvResult.text = "Email not found."
                        else -> tvResult.text = "Error: ${response.message()}"
                    }
                }
                tvResult.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                tvResult.text = "Error: ${t.message}"
                tvResult.visibility = View.VISIBLE
            }
        })
    }
}
