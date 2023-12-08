package reservation.controller

import common.exception.NotFoundResourceException
import reservation.dto.AccountDetailReadDto
import reservation.dto.ReservationCreateDto
import reservation.dto.ReservationReadDto
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

        val reservationDto = ReservationCreateDto(
            name = name,
            roomNumber = roomNumber,
            checkIn = checkIn,
            checkOut = checkOut,
        )

        reservationService.addReservation(reservationDto)
    }

    override fun printAllReservations(isSorted: Boolean) {
        val reservations = reservationService.getAllReservations(isSorted)
        printReservations(reservations)

        println()
    }

    override fun printAccountDetails() {
        val result: ArrayList<AccountDetailReadDto> = arrayListOf()
        try {
            println("조회하실 사용자를 입력해주세요.")
            val name = readln().trim()
            result.addAll(reservationService.findAccountDetailByName(name))
        } catch (_: NotFoundResourceException) {
            println("예약된 사용자를 찾을 수 없습니다.")
        }

        result.forEach {
            println("${it.id} : ${it.description}으로 ${it.amount}원 ${it.typeKoreanName}되었습니다.")
        }
    }

    override fun updateOrCancelReservation() {
        println("예약을 변경할 사용자 이름을 입력하세요")
        val name = readln().trim()

        while(true) {
            val reservations = reservationService.findReservationsByName(name)
            if (reservations.isEmpty()) {
                println("사용자 이름으로 예약된 목록을 찾을 수 없습니다.")
                return
            }

            println("$name 님이 예약한 목록입니다. 변경하실 예약번호를 입력해주세요. 탈출은 exit 입력")
            printReservations(reservations)

            print("입력: ")
            val rawCommand = readln().trim()
            if (rawCommand == "exit"){
                println("예약 변경/취소 화면을 나갑니다.\n")
                return
            }

            val id = try {
                rawCommand.toLong()
            } catch (e: Exception) {
                println("숫자를 입력해주세요.")
                continue
            }

            val reservation = reservations.filter { it.id == id }.firstOrNull()
            if (reservation == null) {
                println("범위에 없는 예약번호 입니다.")
                continue
            }

            println("해당 예약을 어떻게 하시겠습니까? (1: 변경, 2: 취소, 이외 번호: 메뉴로 돌아가기")
            val updateOrDelete = readln().trim()
            when(updateOrDelete) {
                "1" -> {
                    println("체크인/체크아웃 날짜를 변경할 수 있습니다.")
                    val checkIn = inputCheckInDate(reservation.roomNumber)
                    val checkOut = inputCheckOutDate(reservation.roomNumber, checkIn)

                    val updateDto = ReservationCreateDto(
                        name = reservation.name,
                        roomNumber = reservation.roomNumber,
                        checkIn = checkIn,
                        checkOut = checkOut,
                    )

                    reservationService.updateReservation(
                        id = id,
                        updateDto = updateDto
                    )
                }
                "2" -> {
                    println("[취소 유의사항]")
                    println("체크인 3일 이전 취소 예약금 환불 불가")
                    println("체크인 5일 이전 취소 예약금의 30% 환불")
                    println("체크인 7일 이전 취소 예약금의 50% 환불")
                    println("체크인 14일 이전 취소 예약금의 80% 환불")
                    println("체크인 30일 이전 취소 예약금의 100% 환불")

                    reservationService.remove(reservation.id)

                    println("취소가 완료되었습니다.")
                }
                else -> {
                    return
                }
            }

        }

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
            val roomNumber = try {
                raw.toInt()
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

            val raw = readln().trim()
            val checkIn = try {
                LocalDate.parse(raw, dateFormat)
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
            val raw = readln().trim()

            val checkOut = try {
                LocalDate.parse(raw, dateFormat)
            } catch (e: Exception) {
                println("올바른 날짜 형식이 아닙니다.")
                continue
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

    private fun printReservations(reservations: ArrayList<ReservationReadDto>) {
        for (r in reservations) {
            val content = String.format(" 번호: %5s,", r.id.toString()) +
                    String.format("\t사용자: %8s,", r.name) +
                    String.format("\t방번호: %4s호,", r.roomNumber) +
                    String.format("\t체크인: %10s,", r.checkIn) +
                    String.format("\t체크아웃: %10s,", r.checkOut) +
                    String.format("\t요금: %7s", r.roomFee.toString())
            println(content)
        }
    }
}