package reservation.dto

import common.util.CustomLocalDateHelper.transferLocalDateString
import reservation.entity.Reservation

data class ReservationReadDto(
    val id: Long,
    val name: String,
    val checkIn: String,
    val checkOut: String,
    val roomNumber: Int,
    val roomFee: Int,
) {
    companion object {
        fun from(
            reservation: Reservation
        ): ReservationReadDto = reservation.let {
            ReservationReadDto(
                id = it.id,
                name = it.name,
                checkIn = transferLocalDateString(it.checkInDate),
                checkOut = transferLocalDateString(it.checkOutDate),
                roomNumber = it.roomNumber,
                roomFee = it.roomFee
            )
        }
    }
}