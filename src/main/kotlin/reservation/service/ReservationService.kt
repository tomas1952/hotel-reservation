package reservation.service

import reservation.entity.Reservation
import java.time.LocalDate

interface ReservationService {
    fun addReservation(r: Reservation)
    fun validateCheckInDate(roomNumber: Int, date: LocalDate)
    fun validateCheckOutDate(roomNumber: Int, date: LocalDate)
    fun validateCheckInOutDate(roomNumber: Int, checkInDate: LocalDate, checkOutDate: LocalDate)
    fun remove(id: Long)
    fun getAllReservations(isSorted: Boolean = false): ArrayList<Reservation>
}