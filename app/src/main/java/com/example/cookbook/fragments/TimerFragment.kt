package com.example.cookbook.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cookbook.*


private lateinit var mediaPlayer: MediaPlayer

private const val ARG_PARAM_SECONDS = "seconds"
private const val ARG_PARAM_RUNNING = "running"
private const val ARG_PARAM_WAS_RUNNING = "wasRunning"

class TimerFragment : Fragment() {
    private var seconds = 0
    private var running = false
    private var wasRunning = false

    private lateinit var secondsView: EditText
    private lateinit var minutesView: EditText
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var pauseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            seconds = it.getInt(ARG_PARAM_SECONDS)
            running = it.getBoolean(ARG_PARAM_RUNNING)
            wasRunning = it.getBoolean(ARG_PARAM_WAS_RUNNING)
        }
    }

    inner class MinMaxFilter(private val minValue: Int, private val maxValue: Int) : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dStart: Int,
            dEnd: Int
        ): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(minValue, maxValue, input)) {
                    return null
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        setupViews(view)
        setupListeners()
        runTimer()
        refreshUIStates()

        return view
    }

    private fun setupViews(view: View) {
        secondsView = view.findViewById(R.id.timer_seconds)
        minutesView = view.findViewById(R.id.timer_minutes)
        startButton = view.findViewById(R.id.start_timer_button)
        stopButton = view.findViewById(R.id.stop_timer_button)
        pauseButton = view.findViewById(R.id.pause_timer_button)

        secondsView.filters = arrayOf(MinMaxFilter(0, 59))
        minutesView.filters = arrayOf(MinMaxFilter(0, 180))
    }

    private fun setupListeners() {
        startButton.setOnClickListener { onStartButtonClick() }
        pauseButton.setOnClickListener { onPauseButtonClick() }
        stopButton.setOnClickListener { onStopButtonClick() }
    }

    private fun onStartButtonClick() {
        if (secondsView.text.isEmpty() || minutesView.text.isEmpty()) {
            return
        }

        seconds = secondsView.text.toString().toInt() + minutesView.text.toString().toInt() * 60
        if (seconds <= 0) {
            return
        }

        running = true
        refreshUIStates()
    }

    private fun onPauseButtonClick() {
        running = false

        refreshUIStates()
    }


    private fun onStopButtonClick() {
        running = false
        seconds = 0
        updateTimerUI()
        refreshUIStates()

        if (isMediaPlayerPlaying()) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }
    private fun isMediaPlayerPlaying(): Boolean {
        return try {
            ::mediaPlayer.isInitialized && mediaPlayer.isPlaying
        } catch (e: IllegalStateException) {
            false
        }
    }

    private fun playSound() {
        mediaPlayer = MediaPlayer.create(context, R.raw.timer_sound)
        mediaPlayer.setVolume(0f, 0f) // Start with zero volume
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        val handler = Handler(Looper.getMainLooper())
        val maxVolume = 1f
        val volumeStep = 0.05f
        val intervalMillis = 200L

        handler.post(object : Runnable {
            private var currentVolume = 0f

            override fun run() {
                currentVolume += volumeStep
                if (currentVolume <= maxVolume) {
                    mediaPlayer.setVolume(currentVolume, currentVolume)
                    handler.postDelayed(this, intervalMillis)
                } else {
                    // Reached max volume, stop increasing volume
                }
            }
        })
    }

    private fun stopTimer() {
        running = false
        refreshUIStates()
        playSound()
    }

    private fun refreshUIStates() {
        val isEnabled = !running && seconds == 0
        secondsView.isEnabled = isEnabled
        minutesView.isEnabled = isEnabled

        startButton.visibility =
            if (!running || (!running && seconds > 0)) View.VISIBLE else View.GONE
        pauseButton.visibility = View.VISIBLE
        stopButton.visibility = View.VISIBLE
    }
    private fun updateTimerUI() {
        secondsView.setText("%02d".format(seconds % 60))
        minutesView.setText("%02d".format(seconds / 60))
    }

    private fun runTimer() {
        val handler = Handler(Looper.getMainLooper())

        handler.post {
            if (running && seconds == 0) {
                stopTimer()
            } else if (running) {
                seconds--
                updateTimerUI()
            }

            handler.postDelayed({ runTimer() }, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
            refreshUIStates()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_PARAM_SECONDS, seconds)
        outState.putBoolean(ARG_PARAM_RUNNING, running)
        outState.putBoolean(ARG_PARAM_WAS_RUNNING, wasRunning)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimerFragment()
    }
}