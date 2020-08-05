package pet.ca.podcastexercise.play

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_play.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pet.ca.podcastexercise.MainActivity
import pet.ca.podcastexercise.R
import pet.ca.podcastexercise.utils.load
import pet.ca.podcastexercise.utils.toPlayTime

class PlayFragment : Fragment() {

    private val args: PlayFragmentArgs by navArgs()
    private lateinit var mediaPlayer: MediaPlayer
    private var mediaDuration: Int = -1
    private var alive = true
    private var currentBufferPercent = 0

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

        play.isSelected = false
        play.isEnabled = false
        forward.isEnabled = false
        replay.isEnabled = false

        initButtonAction()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val to = (mediaDuration.toDouble() * progress.toDouble() / 100).toInt()
                    if (to > mediaPlayer.currentPosition) forwardSeek(to)
                    else replaySeek(to)
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                        play.isSelected = true
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onStart() {
        super.onStart()
        initMediaPlayer()
    }

    override fun onStop() {
        super.onStop()
        alive = false
        mediaPlayer.stop()
        mediaPlayer.release()
        play.isSelected = false
        play.isEnabled = false
        forward.isEnabled = false
        replay.isEnabled = false
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(args.episode.contentUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaDuration = it.duration
            endTime.text = mediaDuration.toLong().toPlayTime()
            loading.visibility = View.GONE
            it.start()
            play.isSelected = true
            play.isEnabled = true
            forward.isEnabled = true
            replay.isEnabled = true
            alive = true
            startListenProgress()
        }
        mediaPlayer.setOnBufferingUpdateListener { _, percent ->
            currentBufferPercent = percent
            seekBar.secondaryProgress = percent
        }
        mediaPlayer.setOnCompletionListener {
            play.isSelected = false
        }
    }

    private fun startListenProgress() {
        GlobalScope.launch(Dispatchers.IO) {
            while (alive) {
                seekBar.progress =
                    ((mediaPlayer.currentPosition.toDouble() / mediaDuration.toDouble()) * 100).toInt()
                GlobalScope.launch(Dispatchers.Main) {
                    currentTime.text = mediaPlayer.currentPosition.toLong().toPlayTime()
                }
                delay(1000)
            }
        }
    }

    private fun initButtonAction() {
        play.setOnClickListener {
            if (it.isSelected) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
            it.isSelected = !it.isSelected
        }

        forward.setOnClickListener {
            forwardSeek(mediaPlayer.currentPosition + 30000)
        }

        replay.setOnClickListener {
            replaySeek(mediaPlayer.currentPosition - 30000)
        }
    }

    private fun forwardSeek(to: Int) {
        val currentBuffered =
            (mediaDuration.toDouble() * currentBufferPercent.toDouble() / 100).toInt()
        mediaPlayer.seekTo(
            if (to > currentBuffered) {
                currentBuffered
            } else {
                to
            }
        )
    }

    private fun replaySeek(to: Int) {
        mediaPlayer.seekTo(
            if (to < 0) {
                0
            } else {
                to
            }
        )
    }

}