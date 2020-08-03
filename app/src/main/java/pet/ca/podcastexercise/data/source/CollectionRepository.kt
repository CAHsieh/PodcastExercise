package pet.ca.podcastexercise.data.source

import pet.ca.podcastexercise.data.Collection

class CollectionRepository : ICollectionRepository {
    override suspend fun retrieveCollections(callback: (Collection) -> Unit) {
        TODO("Not yet implemented")
    }
}