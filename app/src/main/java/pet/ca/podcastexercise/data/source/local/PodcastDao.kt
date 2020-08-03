package pet.ca.podcastexercise.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pet.ca.podcastexercise.data.Podcast

@Dao
interface PodcastDao {

    @Query("Select * from podcast limit :limit offset :offset")
    fun loadPodcast(limit: Int, offset: Int): List<Podcast>

    @Query("Select * from podcast")
    fun loadAllPodcast(): List<Podcast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(podcastList: List<Podcast>)

}