package pet.ca.podcastexercise.data.source

import pet.ca.podcastexercise.data.Podcast

class PodcastRepository : IPodcastRepository {


    override suspend fun retrievePodcast(callback: (List<Podcast>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveMorePodcast(callback: (List<Podcast>) -> Unit) {
        TODO("Not yet implemented")
    }

}