package pet.ca.podcastexercise.castlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cast.view.*
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.data.Podcast
import pet.ca.podcastexercise.utils.DiffUtilCallbackImpl
import pet.ca.podcastexercise.utils.load
import javax.inject.Inject

class CastListAdapter @Inject constructor() : RecyclerView.Adapter<CastListAdapter.ViewHolder>() {

    private val podcastList: MutableList<Podcast> = mutableListOf()

    var onPodcastClickListener: ((View, Podcast) -> Unit)? = null

    var needMoreDataCallback: (() -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))

    override fun getItemCount(): Int = podcastList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(podcastList[position])

        if (position == podcastList.size - 1) {
            needMoreDataCallback?.invoke()
        }
    }

    fun updateList(list: List<Podcast>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallbackImpl(podcastList, list))
        podcastList.clear()
        podcastList.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Podcast) {

            itemView.thumbnail.load(cast.artworkUrl100)
            itemView.artistName.text = cast.artistName
            itemView.name.text = cast.name

            itemView.thumbnail.transitionName = "thumbnail_${adapterPosition}"
            itemView.artistName.transitionName = "artistName_${adapterPosition}"
            itemView.name.transitionName = "name_${adapterPosition}"

            itemView.setOnClickListener {
                onPodcastClickListener?.invoke(it, cast)
            }
        }
    }
}