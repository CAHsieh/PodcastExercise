package pet.ca.podcastexercise.data.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.CollectionAndAllContentEntity

class CollectionRepository(
    private val collectionRemoteDataSource: ICollectionDataSource,
    private val collectionLocalDataSource: ICollectionDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICollectionRepository {

    override suspend fun retrieveCollections(callback: (CollectionAndAllContentEntity?) -> Unit) {
        collectionLocalDataSource.retrieveCollection { collection ->
            if (collection == null) {
                GlobalScope.launch(ioDispatcher) {
                    collectionRemoteDataSource.retrieveCollection {
                        callback.invoke(it)
                    }
                }
            } else {
                callback.invoke(collection)
            }
        }
    }

}