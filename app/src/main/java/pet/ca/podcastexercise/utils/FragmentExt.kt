package pet.ca.podcastexercise.utils

import androidx.fragment.app.Fragment
import pet.ca.podcastexercise.PodcastApplication
import pet.ca.podcastexercise.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val application = (requireContext().applicationContext as PodcastApplication)
    return ViewModelFactory(
        application.podcastRepository,
        application.collectionRepository,
        this
    )
}