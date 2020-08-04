package pet.ca.podcastexercise.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pet.ca.podcastexercise.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .fitCenter()
        .apply(RequestOptions.placeholderOf(R.drawable.loading))
        .into(this)
}