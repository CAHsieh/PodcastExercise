package pet.ca.podcastexercise.castdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cast_detail.*
import kotlinx.android.synthetic.main.item_episode.view.*
import pet.ca.podcastexercise.MainActivity
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.utils.getViewModelFactory
import pet.ca.podcastexercise.utils.load
import javax.inject.Inject

@AndroidEntryPoint
class CastDetailFragment : Fragment() {

    private val viewModel by viewModels<CastDetailViewModel> { getViewModelFactory() }

    private val args: CastDetailFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_podcast)
        return inflater.inflate(R.layout.fragment_cast_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolBar.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_arrow_back_24)

        initView()

        viewModel.collectionLiveData.observe(viewLifecycleOwner, Observer {
            //set true cast if has real data
        })

        viewModel.contentListLiveData.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).dismissLoading()
            adapter.updateList(it)
        })

        setCallback()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun initView() {

        val cast = args.podcast
        thumbnail.load(cast.artworkUrl100)
        artistName.text = cast.artistName
        name.text = cast.name

        //Init View
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setCallback() {
        adapter.onEpisodeClickListener = { view, contentFeedEntity ->
            val action = CastDetailFragmentDirections.actionCastDetailFragmentToPlayFragment(
                args.podcast.artworkUrl100,
                contentFeedEntity.contentFeed
            )

            val extras = FragmentNavigatorExtras(
                thumbnail to "thumbnail",
                view.episodeName to "episodeName"
            )

            findNavController().navigate(action, extras)
        }

        adapter.needMoreDataCallback = {
            (activity as MainActivity).showLoading()
            viewModel.loadMoreData()
        }
    }
}