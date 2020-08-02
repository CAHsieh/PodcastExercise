package pet.ca.podcastexercise.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Collection(
    @PrimaryKey
    val artistId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl600: String,
    val collectionId: Int,
    val collectionName: String,
    @Embedded
    val contentFeed: List<ContentFeed>,
    val country: String,
    val genreIds: String,
    val genres: String,
    val releaseDate: String
)