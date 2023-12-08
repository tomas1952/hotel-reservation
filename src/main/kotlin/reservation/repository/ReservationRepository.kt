package reservation.repository
import common.exception.NotFoundResourceException
import reservation.entity.Reservation
import java.time.LocalDate

class ReservationRepository : BaseRepository<Reservation> {
    private var currentId: Long = 0L
    private val repository: HashMap<Long, Reservation> = hashMapOf()

    override fun insert(resource: Reservation): Reservation {
        resource.id = ++currentId
        repository[currentId] = resource
        return resource
    }

    override fun update(id: Long, resource: Reservation): Reservation {
        if (repository[id] == null)
            throw NotFoundResourceException("에약")

        resource.id = id
        repository[id] = resource
        return resource
    }

    override fun findById(id: Long): Reservation {
        val result = repository.values.filter { it.id == id }

        if (result.isEmpty())
            throw NotFoundResourceException("예약")

        return result.first()
    }

    override fun findAll(): ArrayList<Reservation> {
        return ArrayList(repository.values.toList())
    }

    override fun delete(id: Long) {
        if (repository[id] == null)
            throw NotFoundResourceException("예약")

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
            .values
            .filter {
                it.roomNumber == roomNumber
                        && (it.checkInDate.isEqual(checkIn) || it.checkInDate.isBefore(checkIn))
                        && checkIn.isBefore(it.checkOutDate)
            }

        return ArrayList(result)
    }

    fun findAllByRoomNumberAndIncludeCheckOutDate(
        roomNumber: Int,
        checkOut: LocalDate,
    ): ArrayList<Reservation> {
        val result = repository.values
            .filter {
                it.roomNumber == roomNumber
                        && it.checkInDate.isBefore(checkOut)
                        && (checkOut.isEqual(it.checkOutDate) || checkOut.isBefore(it.checkOutDate))
            }

        return ArrayList(result)
    }

    fun findAllByRoomNumberAndFullyIncludedCheckInCheckOut(
        roomNumber: Int,
        checkIn: LocalDate,
        checkOut: LocalDate,
    ): ArrayList<Reservation> {
        val result = repository.values
            .filter {
                it.roomNumber == roomNumber
                        && checkIn.isBefore(it.checkInDate)
                        && checkOut.isAfter(it.checkOutDate)
        }

        return ArrayList(result)
    }

    fun findAllByName(
        name: String,
    ): ArrayList<Reservation> {
        val result = repository.values
            .filter { it.name == name }

        return ArrayList(result)
    }

}