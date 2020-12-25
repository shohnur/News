package all.news.network

import all.news.models.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Client {

    @GET("top-headlines")
    fun getTops(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Call<NewsData>

    @GET("top-headlines")
    fun getTopsByCategory(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsData>

    @GET("everything")
    fun getSearchingData(
        @Query("qInTitle") text: String?,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Call<NewsData>
}