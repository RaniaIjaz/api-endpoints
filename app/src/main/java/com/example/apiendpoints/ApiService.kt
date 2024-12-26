package com.example.apiendpoints

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("register.php")
    fun registerUser(@Body registerData: Map<String, String>): Call<Map<String, String>>

    @POST("login.php")
    fun loginUser(@Body loginData: Map<String, String>): Call<Map<String, String>>

    @POST("forgot_password.php")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<Map<String, String>>


    @GET("display_users.php") // Replace with your actual endpoint
    fun getUserDetails(@HeaderMap headers: Map<String, String>): Call<Map<String, Any>>
}




//package com.example.apiendpoints
//
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface ApiService {
//
//    @POST("register")  // Replace with your PHP register API endpoint
//    fun registerUser(@Body user: Users): Call<Response>
//
//    @POST("login")  // Replace with your PHP login API endpoint
//    fun loginUser(@Body user: Users): Call<Response>
//
//    @POST("forgot_password")
//    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<Response>
//
//    @POST("user")  // Replace with your PHP display user API endpoint
//    fun displayUser(@Body token: TokenRequest): Call<Users>
//}
