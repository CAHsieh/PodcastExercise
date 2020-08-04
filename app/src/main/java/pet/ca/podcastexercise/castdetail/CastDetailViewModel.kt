package pet.ca.podcastexercise.castdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.data.CollectionEntity
import pet.ca.podcastexercise.data.ContentFeedEntity
import pet.ca.podcastexercise.data.source.ICollectionRepository

class CastDetailViewModel constructor(
    private val collectionRepository: ICollectionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _collectionLiveData: MutableLiveData<CollectionEntity> = MutableLiveData()
    val collectionLiveData: LiveData<CollectionEntity> = _collectionLiveData

    private val _contentListLiveData: MutableLiveData<List<ContentFeedEntity>> = MutableLiveData()
    val contentListLiveData: LiveData<List<ContentFeedEntity>> = _contentListLiveData

    private var contentList = listOf<ContentFeedEntity>()
    private var dataSize = 10
    private var isLimited = false

    fun start() {
        GlobalScope.launch(Dispatchers.Main) {
            collectionRepository.retrieveCollections {
                it?.run {
                    _collectionLiveData.postValue(it.collection)

                    contentList = it.contents
                    _contentListLiveData.postValue(contentList.subList(0, dataSize))

                }
            }
        }
    }

    fun loadMoreData() {
        if (!isLimited) {
            dataSize *= 2
            if (contentList.size < dataSize) {
                dataSize = contentList.size
                isLimited = true
            }
        }
        _contentListLiveData.postValue(contentList.subList(0, dataSize))
    }

}