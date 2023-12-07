import controller.MainMenuControllerImpl
import controller.ReservationControllerImpl
import enumeration.MainMenuType.*
import util.CustomLocalDateHelper.transferLocalDateString

fun printRoomReservation(reservations: ArrayList<Reservation>) {
    for (r in reservations) {
        val strCheckIn = transferLocalDateString(r.checkInDate)
        val strCheckOut = transferLocalDateString(r.checkOutDate)

        val content = String.format("\t번호: %d", r.id) +
                    String.format("\t사용자: %5s", r.name) +
                    String.format("\t방번호: %4d호", r.roomNumber) +
                    String.format("\t체크인: %s", strCheckIn) +
                    String.format("\t체크아웃: %s", strCheckOut) +
                    String.format("\t요금: %8d", r.roomFee)
        println(content)
    }
    println()
    println()
}

fun main(args: Array<String>) {
    println("호텔예약 프로그램 입니다.")

    val reservationService = ReservationService()

    val mmController = MainMenuControllerImpl()
    val rController = ReservationControllerImpl(
        reservationService = reservationService
    )

    while(true) {
        mmController.printMainMenu()
        val command = mmController.inputMainMenu()
        when(command) {
            RESERVATION -> {
                rController.inputReservation()
            }
            RESERVATION_LIST -> {
                val reservations = reservationService.getAllReservations()
                printRoomReservation(reservations)
            }
            SORTED_RESERVATION_LIST -> {
                val reservations = reservationService.getAllReservations(isSorted = true)
                printRoomReservation(reservations)
            }
            QUIT -> {
                println("시스템을 종료 합니다.")
                break;
            }
            DEPOSIT_WITHDRAWAL_HISTORY_LIST -> {
                TODO("NOT IMPLEMENTED")
            }
            RESERVATION_CHANGES -> {
                TODO("NOT IMPLEMENTED")
            }
        }
    }

}