package pet.ca.podcastexercise.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pet.ca.podcastexercise.data.CollectionAndAllContentEntity
import pet.ca.podcastexercise.data.CollectionEntity
import pet.ca.podcastexercise.data.ContentFeedEntity

@Dao
interface CollectionDao {

    @Query("Select * from collectionentity")
    fun loadCollection(): List<CollectionAndAllContentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(collection: CollectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContentFeed(contents: List<ContentFeedEntity>)
}