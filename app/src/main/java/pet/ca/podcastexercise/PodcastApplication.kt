package pet.ca.podcastexercise

import android.app.Application
import android.content.Context

class PodcastApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}