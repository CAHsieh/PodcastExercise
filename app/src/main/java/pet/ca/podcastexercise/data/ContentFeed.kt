package pet.ca.podcastexercise.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ContentFeed(
    val contentUrl: String,
    val desc: String,
    val publishedDate: String,
    val title: String
) : Parcelable

fun ContentFeed.toEntity(collectionId: Int): ContentFeedEntity {
    return ContentFeedEntity(
        id = 0,
        collectionId = collectionId,
        contentFeed = this
    )
}

@Entity
data class ContentFeedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val collectionId: Int,
    @Embedded
    val contentFeed: ContentFeed
)