package reservation.repository
interface BaseRepository<T> {
    fun add(resource: T): T
    fun update(id: Long, resource: T): T
    fun findAll(): ArrayList<T>
    fun remove(id: Long)
}