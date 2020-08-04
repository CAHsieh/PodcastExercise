package pet.ca.podcastexercise.castdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_episode.view.*
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.data.ContentFeedEntity
import pet.ca.podcastexercise.utils.DiffUtilCallbackImpl

class EpisodeAdapter(
    private val contentList: MutableList<ContentFeedEntity> = mutableListOf()
) : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    var onEpisodeClickListener: ((View, ContentFeedEntity) -> Unit)? = null

    var needMoreDataCallback: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        )

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentList[position])

        if (position == contentList.size - 1) {
            needMoreDataCallback?.invoke()
        }
    }

    fun updateList(list: List<ContentFeedEntity>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallbackImpl(contentList, list))
        contentList.clear()
        contentList.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(content: ContentFeedEntity) {

            itemView.episodeName.text = content.contentFeed.title
            itemView.episodeName.transitionName = "episodeName_${adapterPosition}"

            itemView.setOnClickListener {
                onEpisodeClickListener?.invoke(it, content)
            }
        }
    }
}