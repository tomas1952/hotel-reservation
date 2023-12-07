package reservation.repository
import common.exception.NotFoundResourceException
import reservation.entity.Reservation

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
}