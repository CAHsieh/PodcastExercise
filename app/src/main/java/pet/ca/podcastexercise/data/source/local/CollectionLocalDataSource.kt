package pet.ca.podcastexercise.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pet.ca.podcastexercise.data.Collection
import pet.ca.podcastexercise.data.source.ICollectionDataSource

class CollectionLocalDataSource internal constructor(
    private val collectionDao: CollectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
)  : ICollectionDataSource {
    override suspend fun retrieveCollection(callback: (Collection) -> Unit) {
        TODO("Not yet implemented")
    }
}