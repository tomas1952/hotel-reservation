import exception.DuplicatedResourceException
import exception.NotFoundResourceException
import java.time.LocalDate

class ReservationService(
    private val reservations: ArrayList<Reservation> = arrayListOf(),
) {
    private var currentId = 0L
    fun add(r: Reservation) {
        validateCheckInOutDate(r.roomNumber, r.checkInDate, r.checkOutDate)

        r.id = ++currentId
        reservations.add(r)
    }

    fun validateCheckInDate(roomNumber: Int, date: LocalDate) {
        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && (it.checkInDate.isEqual(date) || it.checkInDate.isAfter(date))
                    && it.checkOutDate.isBefore(date)
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    fun validateCheckOutDate(roomNumber: Int, date: LocalDate) {
        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && it.checkInDate.isAfter(date)
                    && (it.checkOutDate.isEqual(date) || it.checkOutDate.isBefore(date))
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    fun validateCheckInOutDate(roomNumber: Int, checkInDate: LocalDate, checkOutDate: LocalDate) {
        validateCheckInDate(roomNumber, checkInDate)
        validateCheckOutDate(roomNumber, checkOutDate)

        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && it.checkInDate.isBefore(checkInDate)
                    && it.checkOutDate.isAfter(checkOutDate)
        }

        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    fun remove(id: Long) {
        val filteredReservation = reservations.filter { it.id == id }
        if (filteredReservation.isEmpty()) {
            throw NotFoundResourceException("예약")
        }
        reservations.remove(filteredReservation.get(0))
    }

    fun getAllReservations(isSorted: Boolean = false): ArrayList<Reservation> {
        val list = if (isSorted) {
            reservations.sortedBy { it.checkInDate }.toList()
        } else {
            reservations.toList()
        }
        return ArrayList(list)
    }
}