package reservation.service

import reservation.entity.Reservation
import java.time.LocalDate

interface ReservationService {
    fun addReservation(r: Reservation)
    fun validateCheckInDate(roomNumber: Int, checkIn: LocalDate)
    fun validateCheckOutDate(roomNumber: Int, checkOut: LocalDate)
    fun validateCheckInOutDate(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate)
    fun remove(id: Long)
    fun getAllReservations(isSorted: Boolean = false): ArrayList<Reservation>
}