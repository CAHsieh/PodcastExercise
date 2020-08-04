package pet.ca.podcastexercise.data.source.remote

import pet.ca.podcastexercise.data.DetailData0
import pet.ca.podcastexercise.data.PodcastData1
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("getcasts")
    fun getCasts(): Call<PodcastData1>

    @GET("getcastdetail")
    fun getCastDetail(): Call<DetailData0>
}