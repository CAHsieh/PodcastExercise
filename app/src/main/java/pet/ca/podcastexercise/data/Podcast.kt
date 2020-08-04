package pet.ca.podcastexercise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PodcastData1(
    val `data`: PodcastData2
)

data class PodcastData2(
    val podcast: List<Podcast>
)

@Entity
data class Podcast(
    val artistName: String,
    val artworkUrl100: String,
    @PrimaryKey
    val id: String,
    val name: String
)


