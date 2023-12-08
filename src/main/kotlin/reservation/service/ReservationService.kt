package reservation.service

import reservation.entity.AccountDetail
import reservation.entity.Reservation
import java.time.LocalDate

interface ReservationService {
    fun addReservation(reservation: Reservation)
    fun updateReservation(id: Long, updateReservation: Reservation)
    fun validateCheckInDate(roomNumber: Int, checkIn: LocalDate)
    fun validateCheckOutDate(roomNumber: Int, checkOut: LocalDate)
    fun validateCheckInOutDate(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate)
    fun remove(id: Long)
    fun getAllReservations(isSorted: Boolean = false): ArrayList<Reservation>
    fun findAccountDetailByName(name: String): ArrayList<AccountDetail>
    fun findReservationsByName(name: String): ArrayList<Reservation>
}