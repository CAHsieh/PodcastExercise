package pet.ca.podcastexercise.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.data.source.IPodcastDataSource

class PodcastLocalDataSource internal constructor(
    private val podcastDao: PodcastDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IPodcastDataSource {
    override suspend fun retrievePodcast(limit: Int, callback: (List<Podcast>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveMorePodcast(
        limit: Int,
        offset: Int,
        callback: (List<Podcast>) -> Unit
    ) {
        TODO("Not yet implemented")
    }


}