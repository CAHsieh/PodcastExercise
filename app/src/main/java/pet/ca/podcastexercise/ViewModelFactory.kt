package pet.ca.podcastexercise

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import pet.ca.podcastexercise.castdetail.CastDetailViewModel
import pet.ca.podcastexercise.castlist.CastListViewModel
import pet.ca.podcastexercise.data.source.ICollectionRepository
import pet.ca.podcastexercise.data.source.IPodcastRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val podcastRepository: IPodcastRepository,
    private val collectionRepository: ICollectionRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null

) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(CastListViewModel::class.java) ->
                CastListViewModel(podcastRepository, handle)
            isAssignableFrom(CastDetailViewModel::class.java) ->
                CastDetailViewModel(collectionRepository, handle)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}