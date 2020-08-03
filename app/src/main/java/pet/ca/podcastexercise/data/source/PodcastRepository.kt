package pet.ca.podcastexercise.data.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.Podcast

class PodcastRepository(
    private val podcastRemoteDataSource: IPodcastDataSource,
    private val podcastLocalDataSource: IPodcastDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IPodcastRepository {

    private var dataSize = 10
    private var isLimited = false

    override suspend fun retrievePodcast(callback: (List<Podcast>) -> Unit) {
        podcastLocalDataSource.retrievePodcast(dataSize) { localList ->
            if (localList.isEmpty()) {
                GlobalScope.launch(ioDispatcher) {
                    podcastRemoteDataSource.retrieveAllPodcast { remoteList ->
                        callback(
                            if (remoteList.size > dataSize) {
                                remoteList.subList(0, dataSize)
                            } else {
                                remoteList
                            }
                        )
                    }
                }
            } else {
                callback.invoke(localList)
            }
        }
    }

    override suspend fun retrieveMorePodcast(callback: (List<Podcast>) -> Unit) {
        if (!isLimited) {
            dataSize *= 2
        }

        podcastLocalDataSource.retrievePodcast(dataSize) {
            isLimited = (it.size < dataSize)
            callback.invoke(it)
        }
    }

}