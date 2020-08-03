package pet.ca.podcastexercise.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pet.ca.podcastexercise.data.Collection

@Dao
interface CollectionDao {

    @Query("Select * from collection limit :limit offset :offset")
    fun loadCollection(limit: Int, offset: Int): List<Collection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(collections: Collection)

}