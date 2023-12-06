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
                    && (it.checkInDate.isEqual(date) || it.checkInDate.isBefore(date))
                    && date.isBefore(it.checkOutDate)
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    fun validateCheckOutDate(roomNumber: Int, date: LocalDate) {
        val filtered = reservations.filter {
            it.roomNumber == roomNumber
                    && it.checkInDate.isBefore(date)
                    && (date.isEqual(it.checkOutDate) || date.isBefore(it.checkOutDate))
        }
        if (filtered.isNotEmpty())
            throw DuplicatedResourceException("예약")
    }

    fun validateCheckInOutDate(roomNumber: Int, checkInDate: LocalDate, checkOutDate: LocalDate) {
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

fun main() {
    val service = ReservationService()
    service.add(
        Reservation(
            name = "aaa",
            roomNumber = 101,
            checkInDate = LocalDate.of(2023, 12, 10),
            checkOutDate = LocalDate.of(2023, 12, 11),
            roomFee = 10000,
        )
    )

    service.validateCheckInOutDate(101, LocalDate.of(2023, 12, 9), LocalDate.of(2023, 12, 12))

    println()
}