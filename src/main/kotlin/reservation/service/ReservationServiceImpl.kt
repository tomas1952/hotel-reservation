package reservation.service

import common.exception.DuplicatedResourceException
import reservation.entity.Reservation
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import java.time.LocalDate

class ReservationServiceImpl(
    private val rRepository: ReservationRepository,
    private val aRepository: AccountDetailRepository,
) : ReservationService {
    override fun addReservation(r: Reservation) {
        validateCheckInOutDate(r.roomNumber, r.checkInDate, r.checkOutDate)
        rRepository.add(r)
    }

    override fun validateCheckInDate(roomNumber: Int, checkIn: LocalDate) {
        val result = rRepository.findAllByRoomNumberAndIncludeCheckInDate(
            roomNumber = roomNumber,
            checkIn = checkIn,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckOutDate(roomNumber: Int, checkOut: LocalDate) {
        val result = rRepository.findAllByRoomNumberAndIncludeCheckOutDate(
            roomNumber = roomNumber,
            checkOut = checkOut,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckInOutDate(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate) {
        validateCheckInDate(roomNumber, checkIn)
        validateCheckOutDate(roomNumber, checkOut)

        val result = rRepository.findAllByRoomNumberAndFullyIncludedCheckInCheckOut(
            roomNumber = roomNumber,
            checkIn = checkIn,
            checkOut = checkOut,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun remove(id: Long) {
        rRepository.remove(id)
    }

    override fun getAllReservations(isSorted: Boolean): ArrayList<Reservation> = if (isSorted) {
        rRepository.findSortedAll()
    } else {
        rRepository.findAll()
    }

}