package pet.ca.podcastexercise

import android.app.Application
import android.content.Context
import pet.ca.podcastexercise.data.source.ICollectionRepository
import pet.ca.podcastexercise.data.source.IPodcastRepository

class PodcastApplication : Application() {

    val podcastRepository: IPodcastRepository
        get() = ServiceLocator.providePodcastRepository(this)

    val collectionRepository: ICollectionRepository
        get() = ServiceLocator.provideCollectionRepository(this)

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}