package com.example.apiendpoints

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody


class LogInActivity: AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvResult = findViewById(R.id.tvResult)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                tvResult.text = "Please enter both email and password."
                tvResult.visibility = View.VISIBLE
            }
        }
    }

    // Login API call
    private fun loginUser(email: String, password: String) {
        val loginData = mapOf("email" to email, "password" to password)

        RetrofitInstance.api.loginUser(loginData).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    val token = response.body()?.get("auth_token")
                    if (token != null) {
                        tvResult.text = "Login Successful! Token: $token"
                    } else {
                        tvResult.text = "Login Failed: Invalid credentials or user not registered."
                    }
                } else {
                    // Check for specific error codes (e.g., 401 for Unauthorized)
                    when (response.code()) {
                        401 -> {
                            tvResult.text = "Login Failed: Invalid credentials or user not registered."
                        }
                        else -> {
                            tvResult.text = "Login Failed: ${response.errorBody()?.string()}"
                        }
                    }
                }
                tvResult.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                tvResult.text = "Login Failed: ${t.message}"
                tvResult.visibility = View.VISIBLE
            }
        })
    }
}