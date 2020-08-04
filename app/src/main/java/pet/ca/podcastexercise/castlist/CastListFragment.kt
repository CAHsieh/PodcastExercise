package pet.ca.podcastexercise.castlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cast_list.*
import pet.ca.podcastexercise.MainActivity
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.utils.getViewModelFactory

class CastListFragment : Fragment() {

    private val viewModel by viewModels<CastListViewModel> { getViewModelFactory() }

    private lateinit var adapter: CastListAdapter

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
        adapter = CastListAdapter()
        recyclerView.adapter = adapter
    }

    private fun setCallback() {
        adapter.onPodcastClickListener = { view, exhibit ->
            (activity as MainActivity).showLoading()

//            val action = ExhibitFragmentDirections.actionExhibitFragmentToPlantsFragment(
//                exhibit.E_Name,
//                exhibit
//            )
//
//            val extras = FragmentNavigatorExtras(
//                view.thumbnail to "thumbnailExhibit",
//                view.desView to "exhibitDes"
//            )

            findNavController().navigate(R.id.action_castListFragment_to_castDetailFragment)
        }

        adapter.needMoreDataCallback = {
            (activity as MainActivity).showLoading()
            viewModel.loadMoreData()
        }
    }

}