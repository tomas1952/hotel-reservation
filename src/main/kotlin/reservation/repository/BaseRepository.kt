package reservation.repository
interface BaseRepository<T> {
    fun insert(resource: T): T
    fun update(id: Long, resource: T): T
    fun findAll(): ArrayList<T>
    fun delete(id: Long)
}