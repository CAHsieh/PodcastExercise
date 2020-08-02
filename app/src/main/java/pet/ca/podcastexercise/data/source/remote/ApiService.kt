package pet.ca.podcastexercise.data.source.remote

import pet.ca.podcastexercise.data.DetailData
import pet.ca.podcastexercise.data.PodcastData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("getcasts")
    fun getCasts(): Call<PodcastData>

    @GET("getcastdetail")
    fun getCastDetail(): Call<DetailData>
}