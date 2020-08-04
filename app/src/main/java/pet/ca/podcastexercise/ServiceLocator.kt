package pet.ca.podcastexercise

import android.content.Context
import androidx.room.Room
import pet.ca.podcastexercise.data.source.*
import pet.ca.podcastexercise.data.source.local.CollectionLocalDataSource
import pet.ca.podcastexercise.data.source.local.PodcastDatabase
import pet.ca.podcastexercise.data.source.local.PodcastLocalDataSource
import pet.ca.podcastexercise.data.source.remote.CollectionRemoteDataSource
import pet.ca.podcastexercise.data.source.remote.PodcastRemoteDataSource

object ServiceLocator {

    private var database: PodcastDatabase? = null

    @Volatile
    private var podcastRepository: IPodcastRepository? = null

    @Volatile
    private var collectionRepository: ICollectionRepository? = null

    fun providePodcastRepository(context: Context): IPodcastRepository {
        synchronized(this) {
            return podcastRepository ?: createPodcastRepository(context)
        }
    }

    fun provideCollectionRepository(context: Context): ICollectionRepository {
        synchronized(this) {
            return collectionRepository ?: createCollectionRepository(context)
        }
    }

    private fun createPodcastRepository(context: Context): IPodcastRepository {
        val newRepo = PodcastRepository(
            createPodcastRemoteDataSource(context),
            createPodcastLocalDataSource(context)
        )
        podcastRepository = newRepo
        return newRepo
    }

    private fun createCollectionRepository(context: Context): ICollectionRepository {
        val newRepo = CollectionRepository(
            createCollectionRemoteDataSource(context),
            createCollectionLocalDataSource(context)
        )
        collectionRepository = newRepo
        return newRepo
    }

    private fun createPodcastLocalDataSource(context: Context): IPodcastDataSource {
        val database = database ?: createDatabase(context)
        return PodcastLocalDataSource(database.podcastDao())
    }

    private fun createCollectionLocalDataSource(context: Context): ICollectionDataSource {
        val database = database ?: createDatabase(context)
        return CollectionLocalDataSource(database.collectionDao())
    }

    private fun createPodcastRemoteDataSource(context: Context): IPodcastDataSource {
        val database = database ?: createDatabase(context)
        return PodcastRemoteDataSource(database.podcastDao())
    }

    private fun createCollectionRemoteDataSource(context: Context): ICollectionDataSource {
        val database = database ?: createDatabase(context)
        return CollectionRemoteDataSource(database.collectionDao())
    }

    private fun createDatabase(context: Context): PodcastDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            PodcastDatabase::class.java,
            "PodcastExercise.db"
        ).build()
        database = result
        return result
    }

}