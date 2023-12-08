package reservation.dto

import common.util.RoomFeeCalculator
import reservation.entity.Reservation
import java.time.LocalDate

data class ReservationCreateDto(
    val name: String,
    val checkIn: LocalDate,
    val checkOut: LocalDate,
    val roomNumber: Int,
) {
    fun toReservation(): Reservation = Reservation(
        name = name,
        checkInDate = checkIn,
        checkOutDate = checkOut,
        roomNumber = roomNumber,
        roomFee = RoomFeeCalculator.calcRoomFee(checkIn, checkOut)
    )
}