import main.MainMenuControllerImpl
import reservation.controller.ReservationControllerImpl
import common.enumeration.MainMenuType.*
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import reservation.service.ReservationServiceImpl

fun main(args: Array<String>) {
    println("호텔예약 프로그램 입니다.")

    val reservationRepository = ReservationRepository()
    val accountDetailRepository = AccountDetailRepository()

    val reservationService = ReservationServiceImpl(
        reservationRepository = reservationRepository,
        accountDetailRepository = accountDetailRepository,
    )

    val mainMenuController = MainMenuControllerImpl()
    val reservationController = ReservationControllerImpl(
        reservationService = reservationService
    )

    while(true) {
        mainMenuController.printMainMenu()
        when(mainMenuController.inputMainMenu()) {
            RESERVATION -> {
                reservationController.inputReservation()
            }
            RESERVATION_LIST -> {
                reservationController.printAllReservations(false)
            }
            SORTED_RESERVATION_LIST -> {
                reservationController.printAllReservations(true)
            }
            QUIT -> {
                println("시스템을 종료 합니다.")
                break;
            }
            DEPOSIT_WITHDRAWAL_HISTORY_LIST -> {
                reservationController.printAccountDetails()
            }
            RESERVATION_CHANGES -> {
                TODO("NOT IMPLEMENTED")
            }
        }
    }

}