package com.example.vinhtv.myplaid.datasource

import com.example.vinhtv.myplaid.datasource.model.Shot
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// https://api.dribbble.com/v1/shots?page=1&per_page=30
// https://www.designernews.co/api/v1/stories?page=1&client_id=%3Cyour%20designer%20news%20client%20id%3E
// https://dribbble.com/search?q=Material%20Design&page=1&per_page=12&s=latest
class FeedRepository {

    companion object {

        private val dribbleService: DribbbleSearchService = Retrofit.Builder()
                .baseUrl(DribbbleSearchService.ENDPOINT)
                .client(httpClient())
                .addConverterFactory(DribbbleSearchConverter.Factory())
                .build().create(DribbbleSearchService::class.java)

        private fun httpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
        }
    }

    fun fetch(): List<Shot>? {
        val request = dribbleService.search("Material Design", 1, 12, "latest")
        val response = request.execute()
        if(response.isSuccessful) {
            return response.body()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}