package pet.ca.podcastexercise.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_play.*
import pet.ca.podcastexercise.MainActivity
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.utils.load

class PlayFragment : Fragment() {

    private val args: PlayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_podcast)
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolBar.navigationIcon =
            view.context.getDrawable(R.drawable.ic_baseline_arrow_back_24)

        thumbnail.load(args.thumbnailUrl)
        episodeName.text = args.episode.title
    }
}