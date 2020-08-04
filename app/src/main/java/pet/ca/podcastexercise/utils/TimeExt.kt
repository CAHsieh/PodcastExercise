package pet.ca.podcastexercise.utils

import java.util.concurrent.TimeUnit

fun Long.toPlayTime(): String {
    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % TimeUnit.MINUTES.toSeconds(1)
    )
}