package pet.ca.podcastexercise.data.source.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pet.ca.podcastexercise.data.Collection
import pet.ca.podcastexercise.data.source.ICollectionDataSource
import pet.ca.podcastexercise.data.source.local.CollectionDao

class CollectionRemoteDataSource internal constructor(
    private val collectionDao: CollectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICollectionDataSource {
    override suspend fun retrieveCollection(callback: (Collection) -> Unit) {
        TODO("Not yet implemented")
    }
}