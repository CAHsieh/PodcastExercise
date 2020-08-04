package pet.ca.podcastexercise.data.source.remote

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.CollectionAndAllContentEntity
import pet.ca.podcastexercise.data.DetailData
import pet.ca.podcastexercise.data.source.ICollectionDataSource
import pet.ca.podcastexercise.data.source.local.CollectionDao
import pet.ca.podcastexercise.data.toEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionRemoteDataSource internal constructor(
    private val collectionDao: CollectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICollectionDataSource {
    override suspend fun retrieveCollection(callback: (CollectionAndAllContentEntity?) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            ApiManager.getCastDetail(object : Callback<DetailData> {
                override fun onFailure(call: Call<DetailData>, t: Throwable) {
                    Log.e(CollectionRemoteDataSource::class.java.simpleName, t.message!!)
                    callback.invoke(null)
                }

                override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {
                    if (response.isSuccessful && response.body() != null) {
                        val collection = (response.body() as DetailData).collection
                        collectionDao.insert(collection.toEntity())
                        collectionDao.insertContentFeed(
                            collection.contentFeed.map {
                                it.toEntity(collection.collectionId)
                            }
                        )

                        callback.invoke(collectionDao.loadCollection()[0])
                    } else {
                        onFailure(call, RuntimeException("onResponse Failure."))
                    }
                }
            })
        }
    }
}