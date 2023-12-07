import controller.MainMenuControllerImpl
import enumeration.MainMenuType.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun inputRoomReservation(reservationService: ReservationService): Reservation {
    var name = ""
    while(true) {
        println("예약자분의 성함을 알려주세요")
        val raw = readln()
        name = raw.trim()
        if (name.isEmpty() || name.isBlank()) {
            println("이름은 1자이상 이어야 합니다.")
            continue
        }
        break;
    }

    var roomNumber = 0
    while(true) {
        println("예약자할 방번호를 입력해주세요")
        val raw = readln()
        try {
            roomNumber = raw.trim().toInt()
        } catch (e: Exception) {
            println("방번호는 숫자를 입력해주세요!!!")
            continue
        }

        if (roomNumber < 100 || roomNumber > 999) {
            println("방번호는 100 ~ 999 사이를 입력해주세요.")
            continue
        }

        break;
    }

    val now = LocalDate.now()
    var checkInDate:LocalDate = now

    while(true) {
        println("체크인 날짜를 입력해주세요. (e.g.: 20230631)")
        val raw = readln()
        try {
            checkInDate = LocalDate.parse(raw, DateTimeFormatter.ofPattern("yyyyMMdd"))
        } catch (e: Exception) {
            println("올바른 날짜 형식이 아닙니다.")
            continue
        }

        if (checkInDate.isBefore(now)) {
            println("체크인은 지난날을 선택할 수 없습니다.")
            continue
        }

        try {
            reservationService.validateCheckInDate(roomNumber, checkInDate)
        } catch (e: Exception) {
            println("해당 날짜는 체크인 할 수 없습니다.")
            continue
        }


        break;
    }

    var checkOutDate:LocalDate = now
    while(true) {
        println("체크아웃 날짜를 입력해주세요. (e.g.: 20230631)")
        val raw = readln()
        try {
            checkOutDate = LocalDate.parse(raw, DateTimeFormatter.ofPattern("yyyyMMdd"))
        } catch (e: Exception) {
            println("올바른 날짜 형식이 아닙니다.")
        }

        if (checkOutDate.isEqual(checkInDate) || checkOutDate.isBefore(checkInDate)) {
            println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
            continue
        }

        try {
            reservationService.validateCheckInOutDate(roomNumber, checkInDate, checkOutDate)
        } catch (e: Exception) {
            println("해당 날짜는 체크아웃 할 수 없습니다.")
            continue
        }
        break;
    }

    println()
    println()

    return Reservation(
        name = name,
        roomNumber = roomNumber,
        checkInDate = checkInDate,
        checkOutDate = checkOutDate,
        roomFee = Random.nextInt(5000, 10000) * 10
    )
}

fun localDateToString(a: LocalDate): String {
    val strYear = a.year.toString()
    val strMonth = a.monthValue.toString().padStart(2, '0')
    val strDay = a.dayOfMonth.toString().padStart(2, '0')

    return "${strYear}-${strMonth}-${strDay}"
}

fun printRoomReservation(reservations: ArrayList<Reservation>) {
    for (r in reservations) {
        val strCheckIn = localDateToString(r.checkInDate)
        val strCheckOut = localDateToString(r.checkOutDate)

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

    while(true) {
        mmController.printMainMenu()
        val command = mmController.inputMainMenu()
        when(command) {
            RESERVATION -> {
                val reservation = inputRoomReservation(reservationService)
                reservationService.add(reservation)
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