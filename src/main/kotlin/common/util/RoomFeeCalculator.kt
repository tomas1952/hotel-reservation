package common.util

import java.time.LocalDate
import java.time.Period
import kotlin.random.Random

object RoomFeeCalculator {
    fun calcRoomFee(checkIn: LocalDate, checkOut: LocalDate): Int {
        val days = Period.between(checkIn, checkOut).days
        val prices = (1..days).map { Random.nextInt(70000, 80000) }

        return prices.sum()
    }
}