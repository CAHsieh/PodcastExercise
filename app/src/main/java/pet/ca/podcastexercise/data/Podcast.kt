package pet.ca.podcastexercise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Podcast(
    val artistName: String,
    val artworkUrl100: String,
    @PrimaryKey
    val id: String,
    val name: String
)