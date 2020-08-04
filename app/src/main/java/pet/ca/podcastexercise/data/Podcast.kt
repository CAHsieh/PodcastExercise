package pet.ca.podcastexercise.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class PodcastData1(
    val `data`: PodcastData2
)

data class PodcastData2(
    val podcast: List<Podcast>
)

@Parcelize
@Entity
data class Podcast(
    val artistName: String,
    val artworkUrl100: String,
    @PrimaryKey
    val id: String,
    val name: String
) : Parcelable


