package com.newsapp.utilities

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object Utils {

    fun formatDate(createdAt: String): String {
        val form = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        form.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val date = form.parse(createdAt)
            val cal = Calendar.getInstance()
            val tz = cal.timeZone
            form.timeZone = tz
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            simpleDateFormat.timeZone = tz //HH:mm
            if (date == null) return ""
            return simpleDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    @ExperimentalTime
    fun getDateDiff(oldDate: Long, newDate: Long): Long {
        return try {
            DurationUnit.HOURS.convert(
                newDate - oldDate,
                DurationUnit.MILLISECONDS
            )
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}