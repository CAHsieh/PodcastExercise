package pet.ca.podcastexercise.data.source

import pet.ca.podcastexercise.data.Podcast

interface IPodcastDataSource {

    suspend fun retrievePodcast(limit: Int, callback: (List<Podcast>) -> Unit)

    suspend fun retrieveAllPodcast(callback: (List<Podcast>) -> Unit)

}

interface IPodcastRepository {

    suspend fun retrievePodcast(callback: (List<Podcast>) -> Unit)

    suspend fun retrieveMorePodcast(callback: (List<Podcast>) -> Unit)

}