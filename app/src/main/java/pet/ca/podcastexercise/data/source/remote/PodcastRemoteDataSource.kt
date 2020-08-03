package pet.ca.podcastexercise.data.source.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.data.source.IPodcastDataSource
import pet.ca.podcastexercise.data.source.local.PodcastDao

class PodcastRemoteDataSource internal constructor(
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