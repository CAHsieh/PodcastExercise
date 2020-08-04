package pet.ca.podcastexercise.data.source.remote

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.CollectionAndAllContentEntity
import pet.ca.podcastexercise.data.DetailData0
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
            ApiManager.getCastDetail(object : Callback<DetailData0> {
                override fun onFailure(call: Call<DetailData0>, t: Throwable) {
                    Log.e(CollectionRemoteDataSource::class.java.simpleName, t.message!!)
                    callback.invoke(null)
                }

                override fun onResponse(call: Call<DetailData0>, response: Response<DetailData0>) {
                    if (response.isSuccessful && response.body() != null) {
                        GlobalScope.launch(ioDispatcher) {
                            val collection = (response.body() as DetailData0).data.collection
                            collectionDao.insert(collection.toEntity())
                            collectionDao.insertContentFeed(
                                collection.contentFeed.map {
                                    it.toEntity(collection.collectionId)
                                }
                            )

                            callback.invoke(collectionDao.loadCollection()[0])
                        }
                    } else {
                        onFailure(call, RuntimeException("onResponse Failure."))
                    }
                }
            })
        }
    }
}