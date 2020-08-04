package pet.ca.podcastexercise.castlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.data.source.IPodcastRepository

class CastListViewModel constructor(
    private val podcastRepository: IPodcastRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _podcastListLiveData: MutableLiveData<List<Podcast>> = MutableLiveData()
    val podcastListLiveData: LiveData<List<Podcast>> = _podcastListLiveData

    fun start() {
        GlobalScope.launch(Dispatchers.Main) {
            podcastRepository.retrievePodcast { podcastList ->
                _podcastListLiveData.postValue(podcastList.toMutableList())
            }
        }
    }

    fun loadMoreData() {
        GlobalScope.launch(Dispatchers.Main) {
            podcastRepository.retrieveMorePodcast { podcastList ->
                _podcastListLiveData.postValue(podcastList.toMutableList())
            }
        }
    }

}