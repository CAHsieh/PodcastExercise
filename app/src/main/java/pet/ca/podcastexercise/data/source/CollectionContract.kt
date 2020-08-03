package pet.ca.podcastexercise.data.source

import pet.ca.podcastexercise.data.Collection

interface ICollectionDataSource {

    suspend fun retrieveCollection(callback: (Collection) -> Unit)

}

interface ICollectionRepository {

    suspend fun retrieveCollections(callback: (Collection) -> Unit)

}