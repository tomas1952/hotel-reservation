import main.MainMenuControllerImpl
import reservation.ReservationControllerImpl
import common.enumeration.MainMenuType.*
import reservation.ReservationServiceImpl

fun main(args: Array<String>) {
    println("호텔예약 프로그램 입니다.")

    val reservationService = ReservationServiceImpl()

    val mmController = MainMenuControllerImpl()
    val rController = ReservationControllerImpl(
        reservationService = reservationService
    )

    while(true) {
        mmController.printMainMenu()
        when(mmController.inputMainMenu()) {
            RESERVATION -> {
                rController.inputReservation()
            }
            RESERVATION_LIST -> {
                rController.printReservation(false)
            }
            SORTED_RESERVATION_LIST -> {
                rController.printReservation(true)
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