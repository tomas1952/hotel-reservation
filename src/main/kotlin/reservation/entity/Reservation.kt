package reservation.entity

import java.time.LocalDate

class Reservation(
    val name: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val roomNumber: Int,
    val roomFee: Int,
) {
    var id: Long = -1L
}