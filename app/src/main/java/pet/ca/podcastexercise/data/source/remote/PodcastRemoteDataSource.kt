package pet.ca.podcastexercise.data.source.remote

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.data.PodcastData
import pet.ca.podcastexercise.data.source.IPodcastDataSource
import pet.ca.podcastexercise.data.source.local.PodcastDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PodcastRemoteDataSource internal constructor(
    private val podcastDao: PodcastDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IPodcastDataSource {
    override suspend fun retrievePodcast(limit: Int, callback: (List<Podcast>) -> Unit) {
        retrieveAllPodcast(callback)
    }

    override suspend fun retrieveAllPodcast(callback: (List<Podcast>) -> Unit) {

        GlobalScope.launch(ioDispatcher) {
            ApiManager.getCasts(object : Callback<PodcastData> {
                override fun onFailure(call: Call<PodcastData>, t: Throwable) {
                    Log.e(PodcastRemoteDataSource::class.java.simpleName, t.message!!)
                    callback.invoke(arrayListOf())
                }

                override fun onResponse(call: Call<PodcastData>, response: Response<PodcastData>) {
                    if (response.isSuccessful && response.body() != null) {
                        val list = (response.body() as PodcastData).podcast
                        podcastDao.insertAll(list)
                        callback.invoke(list)
                    } else {
                        onFailure(call, RuntimeException("onResponse Failure."))
                    }
                }
            })
        }

    }


}