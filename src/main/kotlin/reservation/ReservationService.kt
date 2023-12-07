package reservation

import java.time.LocalDate

interface ReservationService {
    fun add(r: Reservation)
    fun validateCheckInDate(roomNumber: Int, date: LocalDate)
    fun validateCheckOutDate(roomNumber: Int, date: LocalDate)
    fun validateCheckInOutDate(roomNumber: Int, checkInDate: LocalDate, checkOutDate: LocalDate)
    fun remove(id: Long)
    fun getAllReservations(isSorted: Boolean = false): ArrayList<Reservation>
}