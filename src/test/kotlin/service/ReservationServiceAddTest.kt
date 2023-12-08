package service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import reservation.dto.ReservationCreateDto
import reservation.enumeration.AccountDetailType
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import reservation.service.ReservationServiceImpl
import java.time.LocalDate

class ReservationServiceAddTest : BehaviorSpec({
//    beforeTest {
//        reservationRepository = ReservationRepository()
//        accountDetailRepository = AccountDetailRepository()
//        reservationService = ReservationServiceImpl(
//            reservationRepository = reservationRepository,
//            accountDetailRepository = accountDetailRepository,
//        )
//    }

    Given("방예약 정보가 주어졌고,") {
        val reservationRepository = ReservationRepository()
        val accountDetailRepository = AccountDetailRepository()

        val reservationService = ReservationServiceImpl(
            reservationRepository = reservationRepository,
            accountDetailRepository = accountDetailRepository,
        )

        val dto = ReservationCreateDto(
            name = "aaa",
            roomNumber = 101,
            checkIn = LocalDate.of(2024, 1, 1),
            checkOut = LocalDate.of(2024, 1,2),
        )
        When("방예약을 생성하면") {
            reservationService.addReservation(dto)

            Then("방예약 정보는 레파지토리에 저장되고, 출금 정보도 저장되어야 한다.") {
                val reservations = reservationRepository.findAll()

                reservations.size shouldBe 1

                reservations.first().let {
                    it.name shouldBe "aaa"
                    it.roomNumber shouldBe 101
                    it.checkInDate shouldBe LocalDate.of(2024, 1, 1)
                    it.checkOutDate shouldBe LocalDate.of(2024, 1, 2)
                    it.roomFee shouldBeGreaterThan 0
                }

                val accountDetails = accountDetailRepository.findAll()

                accountDetails.size shouldBe 1

                accountDetails.first().let {
                    it.name shouldBe "aaa"
                    it.amount shouldBeGreaterThan 0
                    it.description shouldBe "예약금"
                    it.type shouldBe AccountDetailType.WITHDRAWAL
                }
            }
        }

    }
})