package reservation.controller

interface ReservationController {
    fun inputReservation()
    fun printAllReservations(isSorted: Boolean)
    fun printAccountDetails()
    fun updateOrCancelReservation()
}