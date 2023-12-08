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

    fun calcRefundRoomFee(checkIn: LocalDate, roomFee: Int): Int {
        val days = Period.between(LocalDate.now(), checkIn).days
        return when {
            days < 3 -> 0
            days in 3..4 -> (roomFee.toDouble() * 0.3).toInt()
            days in 5..6 -> (roomFee.toDouble() * 0.5).toInt()
            days in 7..13 -> (roomFee.toDouble() * 0.8).toInt()
            else -> roomFee
        }
    }
}