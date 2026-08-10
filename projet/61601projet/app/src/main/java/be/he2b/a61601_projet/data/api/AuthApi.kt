package be.he2b.a61601_projet.data.api

import be.he2b.a61601_projet.data.requests.AuthRequestBody
import be.he2b.a61601_projet.data.response.authResponse.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/v1/token?grant_type=password")
    @Headers("Content-Type: application/json")
    suspend fun signIn(@Body requestBody: AuthRequestBody): Response<AuthResponse>

    @POST("auth/v1/logout")
    @Headers("Content-Type: application/json")
    suspend fun signOut(@Header("Authorization") token: String): Response<Unit>
}
