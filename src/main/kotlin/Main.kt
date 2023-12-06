import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Random

fun inputAvailableMenu(): Int {
    var curInput = "0"
    val availableString = setOf("1", "2", "3", "4", "5", "6")
    while(true) {
        print("메뉴: ")
        curInput = readln()
        if (availableString.contains(curInput.trim())) {
            return curInput.toInt()
        }
        println("입력할 수 있는 명령어는 1,2,3,4,5,6 입니다.")
    }
}
fun inputRoomReservation(): Reservation {
    var name = ""
    while(true) {
        println("예약자분의 성함을 알려주세요")
        val raw = readln()
        name = raw.trim()
        println(name)
        if (name.isNotEmpty()){
            break;
        }
        println("이름은 1자이상 이어야 합니다.")
    }

    var roomNumber = 0
    while(true) {
        println("예약자할 방번호를 입력해주세요")
        val raw = readln()
        try {
            roomNumber = raw.trim().toInt()
            if (roomNumber > 99 && roomNumber < 1000) {
                break;
            }
            println("방번호는 100 ~ 999 사이를 입력해주세요.")
        } catch (e: Exception) {
            println("방번호는 숫자를 입력해주세요!!!")
        }
    }

    val now = LocalDate.now()
    var checkInDate:LocalDate

    while(true) {
        println("체크인 날짜를 입력해주세요. (e.g.: 20230631)")
        val raw = readln()
        try {
            checkInDate = LocalDate.parse(raw, DateTimeFormatter.ofPattern("yyyyMMdd"))
            if (checkInDate.isEqual(now) || checkInDate.isAfter(now)) {
                break;
            }
            println("체크인은 지난날을 선택할 수 없습니다.")
        } catch (e: Exception) {
            println("올바른 날짜 형식이 아닙니다.")
        }
    }

    var checkOutDate:LocalDate
    while(true) {
        println("체크아웃 날짜를 입력해주세요. (e.g.: 20230631)")
        val raw = readln()
        try {
            checkOutDate = LocalDate.parse(raw, DateTimeFormatter.ofPattern("yyyyMMdd"))
            if (checkOutDate.isAfter(checkInDate)) {
                break;
            }
            println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
        } catch (e: Exception) {
            println("올바른 날짜 형식이 아닙니다.")
        }
    }

    return Reservation(
        name = name,
        roomNumber = roomNumber,
        checkInDate = checkInDate,
        checkOutDate = checkOutDate,
        roomFee = Random().nextInt(50000, 50000)
    )
}

fun main(args: Array<String>) {
    val reservationService = ReservationService()

    var lastedInput = 0
    while(true) {
        println("호텔예약 프로그램 입니다.")
        println("===============================================[메뉴]===============================================")
        println("1.방예약    2.예약목록 출력    3.예약목록 (정렬) 출력    4.시스템 종료    5.금액 입금-출금 내역 목록 출력    6.예약 변경")
        println("===================================================================================================")

        val command = inputAvailableMenu()
        when(command) {
            1 -> {
                val reservation = inputRoomReservation()
                reservationService.add(reservation)
            }
            2 -> {
                val reservations = reservationService.getAllReservations()
            }
            4 -> {
                println("시스템을 종료 합니다.asfdasfsa")
                break;
            }
            else -> {
                println("TBD")
            }
        }
    }

}