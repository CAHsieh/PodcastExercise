package pet.ca.podcastexercise.data.source

import pet.ca.podcastexercise.data.CollectionAndAllContentEntity

interface ICollectionDataSource {

    suspend fun retrieveCollection(callback: (CollectionAndAllContentEntity?) -> Unit)

}

interface ICollectionRepository {

    suspend fun retrieveCollections(callback: (CollectionAndAllContentEntity?) -> Unit)

}