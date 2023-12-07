package reservation.controller

import common.exception.NotFoundResourceException
import common.util.CustomLocalDateHelper.transferLocalDateString
import common.util.RoomFeeCalculator.calcRoomFee
import reservation.entity.AccountDetail
import reservation.entity.Reservation
import reservation.enumeration.AccountDetailHistoryType.DEPOSIT
import reservation.enumeration.AccountDetailHistoryType.WITHDRAWAL
import reservation.service.ReservationServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReservationControllerImpl(
    private val reservationService: ReservationServiceImpl,
) : ReservationController {
    override fun inputReservation() {
        val name = inputName()
        val roomNumber = inputRoomNumber()
        val checkIn = inputCheckInDate(roomNumber)
        val checkOut = inputCheckOutDate(roomNumber, checkIn)
        println("\r")

        val reservation = Reservation(
            name = name,
            roomNumber = roomNumber,
            checkInDate = checkIn,
            checkOutDate = checkOut,
            roomFee = calcRoomFee(checkIn, checkOut)
        )

        reservationService.addReservation(reservation)
    }

    override fun printAllReservations(isSorted: Boolean) {
        val reservations = reservationService.getAllReservations(isSorted)
        printReservations(reservations)

        println("\r")
    }

    override fun printAccountDetails() {
        val result: ArrayList<AccountDetail> = arrayListOf()
        try {
            println("조회하실 사용자를 입력해주세요.")
            val name = readln().trim()
            result.addAll(reservationService.findAccountDetailByName(name))
        } catch (_: NotFoundResourceException) {
            println("예약된 사용자를 찾을 수 없습니다.")
        }

        result.forEach {
            val dw = when(it.type) {
                DEPOSIT -> "입금"
                WITHDRAWAL -> "출금"
            }

            println("${it.id} : ${it.description}으로 ${it.amount}원 ${dw}되었습니다.")
        }
    }

    override fun updateOrCancelReservation() {
        println("예약을 변경할 사용자 이름을 입력하세요")
        val name = readln().trim()
        val reservations = reservationService.findReservationsByName(name)

        if (reservations.isEmpty()) {
            println("사용자 이름으로 예약된 목록을 찾을 수 없습니다.")
            return
        }

        printReservations(reservations)
        while(false) {

        }
        TODO("Not yet implemented")
    }

    // private function
    private fun inputName(): String {
        while(true) {
            println("예약자분의 성함을 알려주세요")
            val name = readln().trim()

            if (name.isEmpty() || name.isBlank()) {
                println("이름은 1자이상 이어야 합니다.")
                continue
            }

            return name
        }
    }
    private fun inputRoomNumber(): Int {
        while(true) {
            println("예약자할 방번호를 입력해주세요")
            val raw = readln().trim()
            var roomNumber = -1
            try {
                roomNumber = raw.toInt()
            } catch (e: Exception) {
                println("방번호는 숫자를 입력해주세요!!!")
                continue
            }

            if (roomNumber < 100 || roomNumber > 999) {
                println("방번호는 100 ~ 999 사이를 입력해주세요.")
                continue
            }

            return roomNumber
        }
    }
    private fun inputCheckInDate(
        roomNumber: Int,
        formatString:String = "yyyyMMdd",
    ): LocalDate {
        val dateFormat = DateTimeFormatter.ofPattern(formatString)

        while(true) {
            val now = LocalDate.now()
            println("체크인 날짜를 입력해주세요. (e.g.: 20230631)")

            val raw = readln()
            var checkIn: LocalDate

            try {
                checkIn = LocalDate.parse(raw, dateFormat)
            } catch (e: Exception) {
                println("올바른 날짜 형식이 아닙니다.")
                continue
            }

            if (checkIn.isBefore(now)) {
                println("체크인은 지난날을 선택할 수 없습니다.")
                continue
            }

            try {
                reservationService.validateCheckInDate(roomNumber, checkIn)
            } catch (e: Exception) {
                println("해당 날짜는 예약되어 있어 체크인 할 수 없습니다.")
                continue
            }

            return checkIn
        }
    }

    private fun inputCheckOutDate(
        roomNumber: Int,
        checkIn: LocalDate,
        formatString: String = "yyyyMMdd",
    ): LocalDate {
        val dateFormat = DateTimeFormatter.ofPattern(formatString)

        while(true) {
            println("체크아웃 날짜를 입력해주세요. (e.g.: 20230631)")
            val raw = readln()
            var checkOut = LocalDate.MIN
            try {
                checkOut = LocalDate.parse(raw, dateFormat)
            } catch (e: Exception) {
                println("올바른 날짜 형식이 아닙니다.")
            }

            if (checkOut.isEqual(checkIn) || checkOut.isBefore(checkIn)) {
                println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                continue
            }

            try {
                reservationService.validateCheckInOutDate(roomNumber, checkIn, checkOut)
            } catch (e: Exception) {
                println("해당 날짜는 예약되어 있어 체크아웃 할 수 없습니다.")
                continue
            }

            return checkOut
        }
    }

    private fun printReservations(reservations: ArrayList<Reservation>) {
        for (r in reservations) {
            val content = String.format(" 번호: %5s,", r.id.toString()) +
                    String.format("\t사용자: %8s,", r.name) +
                    String.format("\t방번호: %4s호,", r.roomNumber) +
                    String.format("\t체크인: %10s,", transferLocalDateString(r.checkInDate)) +
                    String.format("\t체크아웃: %10s,", transferLocalDateString(r.checkOutDate)) +
                    String.format("\t요금: %7s", r.roomFee.toString())
            println(content)
        }
    }
}