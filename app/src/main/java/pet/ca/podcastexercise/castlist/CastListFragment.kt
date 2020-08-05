package pet.ca.podcastexercise.castlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cast_list.*
import kotlinx.android.synthetic.main.item_cast.view.*
import pet.ca.podcastexercise.MainActivity
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.utils.getViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class CastListFragment : Fragment() {

    private val viewModel by viewModels<CastListViewModel> { getViewModelFactory() }

    @Inject
    lateinit var adapter: CastListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cast_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        viewModel.podcastListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
            (activity as MainActivity).dismissLoading()
            if (it.isEmpty()){
                (activity as MainActivity).showNoData()
            }
        })

        setCallback()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun initView() {
        //Init View
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setCallback() {
        adapter.onPodcastClickListener = { view, podcast ->
            (activity as MainActivity).showLoading()

            val action = CastListFragmentDirections.actionCastListFragmentToCastDetailFragment(
                podcast.name,
                podcast
            )

            val extras = FragmentNavigatorExtras(
                view.thumbnail to "thumbnail",
                view.name to "name",
                view.artistName to "artistName"
            )

            findNavController().navigate(action, extras)
        }

        adapter.needMoreDataCallback = {
            (activity as MainActivity).showLoading()
            viewModel.loadMoreData()
        }
    }

}