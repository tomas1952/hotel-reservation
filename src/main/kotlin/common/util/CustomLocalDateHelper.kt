package common.util

import java.time.LocalDate
object CustomLocalDateHelper {
    fun transferLocalDateString(date: LocalDate): String {
        val yearString = date.year.toString()
        val monthString = date.monthValue.toString().padStart(2, '0')
        val dayString = date.dayOfMonth.toString().padStart(2, '0')

        return "${yearString}-${monthString}-${dayString}"
    }
}