package com.sleepTrackerWihtRecyclerview

import android.content.res.Resources
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.gameudacity.R
import com.sleepTrackerWihtRecyclerview.Room.TableEntity
import java.text.SimpleDateFormat

/**these function create a formattend string that can be set in a textView
 * returns a string representing the numeric quality rating
 * */
fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = "OK"
    when (quality) {
        -1 -> qualityString = "__"
        0 -> qualityString = "Very bad"
        1 -> qualityString = "Poor"
        2 -> qualityString = "So-So"
        4 -> qualityString = "Pretty good"
        5 -> qualityString = "Excellent"
    }
    return qualityString
}


/**Take the long milliseconds returned by he system and stored in room
 * and convert it to a nicely formatted string for display
 *
 * EEEE-display the long letter version of the weekday
 * MMM-display the letter abbreviation of the month
 * dd-yyyy-day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format*/

fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}


/** Takes a list of sleepNights and convert and formats it into one string for display
 * for display in a textview we have to supply one string and styles are per textview not applicable per word,
 * so we build formatted string using HTML.This is handy but we will learn a better way of displaying thus data
 * in a future lesson*/

fun formatNights(nights: List<TableEntity>, resource: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resource.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resource.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append(resource.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                append(resource.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.sleepQuality, resource)}<br>")
                append(resource.getString(R.string.hours_slept))

                //Hours
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")

                //Minutes
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}")

                //Seconds
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")

            }
        }

    }

    return HtmlCompat.fromHtml(sb.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY)
}