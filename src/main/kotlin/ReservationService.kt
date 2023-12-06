class ReservationService(
    private val reservations:ArrayList<Reservation> = arrayListOf(),
) {
    private var currentId = 0L
    fun add(reservation: Reservation) {
        reservation.id = ++currentId
        reservations.add(reservation)
    }

    fun remove(id: Long) {
        reservations.removeIf { it.id == id }
    }

    fun getAllReservations(): ArrayList<Reservation> {
        return reservations
    }
}