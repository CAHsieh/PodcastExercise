package pet.ca.podcastexercise.data.source.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pet.ca.podcastexercise.data.DetailData
import pet.ca.podcastexercise.data.PodcastData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {

    private val apiService: ApiService

    init {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://demo4491005.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getCasts(callback: Callback<PodcastData>) {
        apiService.getCasts().enqueue(callback)
    }

    fun getCastDetail(callback: Callback<DetailData>) {
        apiService.getCastDetail().enqueue(callback)
    }
}