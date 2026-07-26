package be.he2b.a61601_projet.data.api

import be.he2b.a61601_projet.data.requests.AuthRequestBody
import be.he2b.a61601_projet.data.response.authResponse.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/v1/token?grant_type=password")
    @Headers(
        "Content-Type: application/json",
        "apikey:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVhcHVhcGVkY3RlaWNocXRvamd4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg4OTY5MDgsImV4cCI6MjA3NDQ3MjkwOH0.GGtjtx3Xn-TMNn53MT-tlrEoxxv3bNCe0yN_-RCBbCo"
    //todo à mettre ds var env
    )
    suspend fun signIn(@Body requestBody: AuthRequestBody): Response<AuthResponse>
    @POST("auth/v1/logout")
    @Headers(
        "Content-Type: application/json",
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVhcHVhcGVkY3RlaWNocXRvamd4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg4OTY5MDgsImV4cCI6MjA3NDQ3MjkwOH0.GGtjtx3Xn-TMNn53MT-tlrEoxxv3bNCe0yN_-RCBbCo"
    )
    suspend fun signOut(@Header("Authorization") token: String): Response<Unit>
}