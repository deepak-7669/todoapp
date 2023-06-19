package com.ds.todoapp.features.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object DateUtil {
    const val TIME_FORMAT_REGEX = "^(0[1-9]|1[0-2]):[0-5][0-9] [APM]{2}$"
    const val HH_MM_AA = "hh:mm a"
    fun isValidTimeFormat(inputTime: String, formatRegex: String): Boolean {
        val pattern = Pattern.compile(formatRegex)
        val matcher = pattern.matcher(inputTime)
        return matcher.matches()
    }

    fun convertTo12HourFormat(hour: Int, minute: Int, format: String): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(calendar.time)
    }

    private fun compareTimes(timeStr1: String, timeStr2: String, timeFormat: String): Boolean {
        val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())

        val currentTimeParsed = dateFormat.parse(timeStr1)
        val timeToCompareParsed = dateFormat.parse(timeStr2)
        return currentTimeParsed?.after(timeToCompareParsed) ?: false
    }

    fun compareTimeWithCurrentFormat(targetTime: String, timeFormat: String): Boolean {
        val currentTime = SimpleDateFormat(timeFormat, Locale.getDefault()).format(Date())
        return compareTimes(currentTime, targetTime, timeFormat = timeFormat)
    }

}