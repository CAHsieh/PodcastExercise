package pet.ca.podcastexercise.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pet.ca.podcastexercise.data.CollectionEntity
import pet.ca.podcastexercise.data.ContentFeedEntity
import pet.ca.podcastexercise.data.Podcast

@Database(
    entities = [Podcast::class, CollectionEntity::class, ContentFeedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PodcastDatabase : RoomDatabase() {

    abstract fun podcastDao(): PodcastDao
    abstract fun collectionDao(): CollectionDao
}

const val DB_QUERY_TIMEOUT = 3000L