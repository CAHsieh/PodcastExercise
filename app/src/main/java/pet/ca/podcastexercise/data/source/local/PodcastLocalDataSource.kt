package pet.ca.podcastexercise.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.data.source.IPodcastDataSource

class PodcastLocalDataSource internal constructor(
    private val podcastDao: PodcastDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IPodcastDataSource {
    override suspend fun retrievePodcast(limit: Int, callback: (List<Podcast>) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            callback.invoke(podcastDao.loadPodcast(limit, 0))
        }
    }

    override suspend fun retrieveAllPodcast(callback: (List<Podcast>) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            callback.invoke(podcastDao.loadAllPodcast())
        }
    }

}