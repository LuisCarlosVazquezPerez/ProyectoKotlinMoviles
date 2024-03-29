package data

import data.model.RemoteResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("discover/movie")
    suspend fun listPoupularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int
    ): RemoteResult
}
