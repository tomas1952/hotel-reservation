package reservation.service

import common.exception.DuplicatedResourceException
import common.exception.NotFoundResourceException
import reservation.entity.AccountDetail
import reservation.entity.Reservation
import reservation.enumeration.AccountDetailHistoryType.WITHDRAWAL
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import java.time.LocalDate

class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val accountDetailRepository: AccountDetailRepository,
) : ReservationService {
    override fun addReservation(reservation: Reservation) {
        validateCheckInOutDate(reservation.roomNumber, reservation.checkInDate, reservation.checkOutDate)
        reservationRepository.insert(reservation)

        val accountDetail = reservation.let {
            AccountDetail(
                name = it.name,
                amount = it.roomFee,
                description = "예약금",
                type = WITHDRAWAL
            )
        }

        accountDetailRepository.insert(accountDetail)
    }

    override fun validateCheckInDate(roomNumber: Int, checkIn: LocalDate) {
        val result = reservationRepository.findAllByRoomNumberAndIncludeCheckInDate(
            roomNumber = roomNumber,
            checkIn = checkIn,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckOutDate(roomNumber: Int, checkOut: LocalDate) {
        val result = reservationRepository.findAllByRoomNumberAndIncludeCheckOutDate(
            roomNumber = roomNumber,
            checkOut = checkOut,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckInOutDate(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate) {
        validateCheckInDate(roomNumber, checkIn)
        validateCheckOutDate(roomNumber, checkOut)

        val result = reservationRepository.findAllByRoomNumberAndFullyIncludedCheckInCheckOut(
            roomNumber = roomNumber,
            checkIn = checkIn,
            checkOut = checkOut,
        )

        if (result.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun remove(id: Long) {
        reservationRepository.delete(id)
    }

    override fun getAllReservations(isSorted: Boolean): ArrayList<Reservation> = if (isSorted) {
        reservationRepository.findSortedAll()
    } else {
        reservationRepository.findAll()
    }

    override fun findAccountDetailByName(name: String): ArrayList<AccountDetail> {
        val result = accountDetailRepository.findAllByName(name)

        if (result.isEmpty()) {
            throw NotFoundResourceException("이름의 입출금 내역")
        }

        return result
    }

    override fun findReservationsByName(name: String): ArrayList<Reservation> {
        return reservationRepository.findAllByName(name)
    }
}