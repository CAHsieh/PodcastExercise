package pet.ca.podcastexercise.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class DetailData0(
    val `data`: DetailData
)

data class DetailData(
    val collection: Collection
)

data class Collection(
    val artistId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl600: String,
    val collectionId: Int,
    val collectionName: String,
    val contentFeed: List<ContentFeed>,
    val country: String,
    val genreIds: String,
    val genres: String,
    val releaseDate: String
)

fun Collection.toEntity(): CollectionEntity {
    return CollectionEntity(
        artistId = this.artistId,
        artistName = this.artistName,
        artworkUrl100 = this.artworkUrl100,
        artworkUrl600 = this.artworkUrl600,
        collectionId = this.collectionId,
        collectionName = this.collectionName,
        country = this.country,
        genreIds = this.genreIds,
        genres = this.genres,
        releaseDate = this.releaseDate
    )
}

@Entity
data class CollectionEntity(
    @PrimaryKey
    val collectionId: Int,
    val artistId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl600: String,
    val collectionName: String,
    val country: String,
    val genreIds: String,
    val genres: String,
    val releaseDate: String
)

data class CollectionAndAllContentEntity(

    @Embedded
    var collection: CollectionEntity,

    @Relation(
        parentColumn = "collectionId",
        entityColumn = "collectionId",
        entity = ContentFeedEntity::class
    )
    var contents: List<ContentFeedEntity>
)