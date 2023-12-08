package reservation.service

import common.exception.DuplicatedResourceException
import common.exception.NotFoundResourceException
import common.util.RoomFeeCalculator.calcRefundRoomFee
import reservation.dto.AccountDetailReadDto
import reservation.dto.ReservationCreateDto
import reservation.dto.ReservationReadDto
import reservation.entity.AccountDetail
import reservation.enumeration.AccountDetailType.DEPOSIT
import reservation.enumeration.AccountDetailType.WITHDRAWAL
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import java.time.LocalDate

class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val accountDetailRepository: AccountDetailRepository,
) : ReservationService {
    override fun addReservation(createDto: ReservationCreateDto) {
        validateCheckInOutDate(createDto.roomNumber, createDto.checkIn, createDto.checkOut)
        val entity = reservationRepository.insert(createDto.toReservation())

        val accountDetail = entity.let {
            AccountDetail(
                name = it.name,
                amount = it.roomFee,
                description = "예약금",
                type = WITHDRAWAL
            )
        }

        accountDetailRepository.insert(accountDetail)
    }

    override fun updateReservation(id: Long, updateDto: ReservationCreateDto) {
        val entity = reservationRepository.findById(id)

        val refund = entity.let {
            AccountDetail(
                name = it.name,
                amount = it.roomFee,
                description = "환불",
                type = DEPOSIT
            )
        }
        accountDetailRepository.insert(refund)

        val updatedEntity = reservationRepository.update(id, updateDto.toReservation())

        val withdraw = updatedEntity.let {
            AccountDetail(
                name = it.name,
                amount = it.roomFee,
                description = "예약금",
                type = WITHDRAWAL
            )
        }

        accountDetailRepository.insert(withdraw)
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
        val r = reservationRepository.findById(id)

        reservationRepository.delete(id)

        val refundRoomFee = calcRefundRoomFee(
            r.checkInDate,
            r.roomFee
        )

        val accountDetail = AccountDetail(
            name = r.name,
            amount = refundRoomFee,
            description = "환불",
            type = DEPOSIT
        )

        accountDetailRepository.insert(accountDetail)
    }

    override fun getAllReservations(isSorted: Boolean): ArrayList<ReservationReadDto> {
        val entities = if (isSorted) {
            reservationRepository.findSortedAll()
        } else {
            reservationRepository.findAll()
        }

        return ArrayList(entities.map(ReservationReadDto::from).toList())
    }


    override fun findAccountDetailByName(name: String): ArrayList<AccountDetailReadDto> {
        val result = accountDetailRepository.findAllByName(name)
        if (result.isEmpty()) {
            throw NotFoundResourceException("이름의 입출금 내역")
        }

        return ArrayList(result.map(AccountDetailReadDto::from).toList())
    }

    override fun findReservationsByName(name: String): ArrayList<ReservationReadDto> {
        val result = reservationRepository.findAllByName(name)

        return ArrayList(result.map(ReservationReadDto::from).toList())
    }
}