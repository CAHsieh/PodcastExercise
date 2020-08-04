package pet.ca.podcastexercise.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.CollectionAndAllContentEntity
import pet.ca.podcastexercise.data.source.ICollectionDataSource

class CollectionLocalDataSource internal constructor(
    private val collectionDao: CollectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICollectionDataSource {
    override suspend fun retrieveCollection(callback: (CollectionAndAllContentEntity?) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            val list = collectionDao.loadCollection()
            callback.invoke(
                if (list.isEmpty()) {
                    null
                } else {
                    list[0]
                }
            )
        }
    }
}