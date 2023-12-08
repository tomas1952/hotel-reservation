package reservation.service

import reservation.dto.AccountDetailReadDto
import reservation.dto.ReservationCreateDto
import reservation.dto.ReservationReadDto
import java.time.LocalDate

interface ReservationService {
    fun addReservation(reservation: ReservationCreateDto)
    fun updateReservation(id: Long, updateReservation: ReservationCreateDto)
    fun validateCheckInDate(roomNumber: Int, checkIn: LocalDate)
    fun validateCheckOutDate(roomNumber: Int, checkOut: LocalDate)
    fun validateCheckInOutDate(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate)
    fun remove(id: Long)
    fun getAllReservations(isSorted: Boolean = false): ArrayList<ReservationReadDto>
    fun findAccountDetailByName(name: String): ArrayList<AccountDetailReadDto>
    fun findReservationsByName(name: String): ArrayList<ReservationReadDto>
}