package be.he2b.a61601_projet

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

public interface MyRetrofitHttpClient {

    @POST ("/auth/v1/token?grant_type=password")
    @Headers(
        "Content-Type: application/json",
        "apikey:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVhcHVhcGVkY3RlaWNocXRvamd4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg4OTY5MDgsImV4cCI6MjA3NDQ3MjkwOH0.GGtjtx3Xn-TMNn53MT-tlrEoxxv3bNCe0yN_-RCBbCo"
    //todo à mettre ds var env
    )
    suspend fun signIn(@Body requestBody:HttpRequestBody)
    suspend fun signOut(@Body requestBody: HttpRequestBody)

    @GET()
    suspend fun fetchData(@Body requestBody: HttpRequestBody)
}
