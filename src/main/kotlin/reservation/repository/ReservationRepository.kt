package reservation.repository
import common.exception.NotFoundResourceException
import reservation.entity.Reservation
import java.time.LocalDate

class ReservationRepository : BaseRepository<Reservation> {
    private var currentId: Long = 0L
    private val repository: HashMap<Long, Reservation> = hashMapOf()

    override fun add(resource: Reservation): Reservation {
        resource.id = ++currentId
        repository[currentId] = resource
        return resource
    }

    override fun update(id: Long, resource: Reservation): Reservation {
        if (repository[id] == null)
            throw NotFoundResourceException("id가 일치하는 예약을 찾을 수 없습니다.")

        resource.id = id
        repository[id] = resource
        return resource
    }

    override fun findAll(): ArrayList<Reservation> {
        return ArrayList(repository.values.toList())
    }

    override fun remove(id: Long) {
        if (repository[id] == null)
            throw NotFoundResourceException("id가 일치하는 예약을 찾을 수 없습니다.")

        repository.remove(id)
    }

    fun findSortedAll(): ArrayList<Reservation> {
        val result = findAll()
        result.sortBy { it.checkInDate }
        return result
    }

    fun findAllByRoomNumberAndIncludeCheckInDate(
        roomNumber: Int,
        checkIn: LocalDate,
    ): ArrayList<Reservation> {
        val result = repository
            .map { it.value }
            .filter {
                it.roomNumber == roomNumber
                        && (it.checkInDate.isEqual(checkIn) || it.checkInDate.isBefore(checkIn))
                        && checkIn.isBefore(it.checkOutDate)
            }

        return ArrayList(result.toList())
    }

    fun findAllByRoomNumberAndIncludeCheckOutDate(
        roomNumber: Int,
        checkOut: LocalDate,
    ): ArrayList<Reservation> {
        val result = repository
            .map { it.value }
            .filter {
                it.roomNumber == roomNumber
                        && it.checkInDate.isBefore(checkOut)
                        && (checkOut.isEqual(it.checkOutDate) || checkOut.isBefore(it.checkOutDate))
            }

        return ArrayList(result.toList())
    }

    fun findAllByRoomNumberAndFullyIncludedCheckInCheckOut(
        roomNumber: Int,
        checkIn: LocalDate,
        checkOut: LocalDate,
    ): ArrayList<Reservation> {
        val result = repository
            .map { it.value }
            .filter {
                it.roomNumber == roomNumber
                        && checkIn.isBefore(it.checkInDate)
                        && checkOut.isAfter(it.checkOutDate)
        }

        return ArrayList(result.toList())
    }

}