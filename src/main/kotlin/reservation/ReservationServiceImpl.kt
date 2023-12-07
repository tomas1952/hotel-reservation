package reservation

import common.exception.DuplicatedResourceException
import common.exception.NotFoundResourceException
import java.time.LocalDate

class ReservationServiceImpl(
    private val reservations: ArrayList<Reservation> = arrayListOf(),
) : ReservationService {
    private var currentId = 0L
    override fun add(r: Reservation) {
        validateCheckInOutDate(r.roomNumber, r.checkInDate, r.checkOutDate)

        r.id = ++currentId
        reservations.add(r)
    }

    override fun validateCheckInDate(roomNumber: Int, date: LocalDate) {
        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && (it.checkInDate.isEqual(date) || it.checkInDate.isBefore(date))
                    && date.isBefore(it.checkOutDate)
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckOutDate(roomNumber: Int, date: LocalDate) {
        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && it.checkInDate.isBefore(date)
                    && (date.isEqual(it.checkOutDate) || date.isBefore(it.checkOutDate))
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun validateCheckInOutDate(roomNumber: Int, checkInDate: LocalDate, checkOutDate: LocalDate) {
        validateCheckInDate(roomNumber, checkInDate)
        validateCheckOutDate(roomNumber, checkOutDate)

        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && checkInDate.isBefore(it.checkInDate)
                    && checkOutDate.isAfter(it.checkOutDate)
        }

        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    override fun remove(id: Long) {
        val filteredReservation = reservations.filter { it.id == id }
        if (filteredReservation.isEmpty()) {
            throw NotFoundResourceException("예약")
        }
        reservations.remove(filteredReservation.get(0))
    }

    override fun getAllReservations(isSorted: Boolean): ArrayList<Reservation> {
        val list = if (isSorted) {
            reservations.sortedBy { it.checkInDate }.toList()
        } else {
            reservations.toList()
        }
        return ArrayList(list)
    }
}