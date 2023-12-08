package service

import common.exception.DuplicatedResourceException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import reservation.dto.ReservationCreateDto
import reservation.entity.AccountDetail
import reservation.entity.Reservation
import reservation.enumeration.AccountDetailType
import reservation.repository.AccountDetailRepository
import reservation.repository.ReservationRepository
import reservation.service.ReservationService
import reservation.service.ReservationServiceImpl
import java.time.LocalDate

class ReservationServiceAddTest : BehaviorSpec() {
    private var reservationRepository: ReservationRepository = ReservationRepository()
    private var accountDetailRepository: AccountDetailRepository = AccountDetailRepository()
    private var reservationService: ReservationService = ReservationServiceImpl(
        reservationRepository = reservationRepository,
        accountDetailRepository = accountDetailRepository,
    )
    init {
        afterTest {
            reservationRepository = ReservationRepository()
            accountDetailRepository = AccountDetailRepository()

            reservationService = ReservationServiceImpl(
                reservationRepository = reservationRepository,
                accountDetailRepository = accountDetailRepository,
            )
        }

        fun setUpReservation() {
            reservationRepository.insert(
                Reservation(
                    name = "aaa",
                    roomNumber = 101,
                    checkInDate = LocalDate.of(2024, 1, 10),
                    checkOutDate = LocalDate.of(2024, 1, 14),
                    roomFee = 70000
                )
            )

            accountDetailRepository.insert(
                AccountDetail(
                    name = "aaa",
                    amount = 70000,
                    description = "예약금",
                    type = AccountDetailType.WITHDRAWAL
                )
            )
        }

        Given("에약 정보가 주어졌고,") {
            val dto = ReservationCreateDto(
                name = "aaa",
                roomNumber = 101,
                checkIn = LocalDate.of(2024, 1, 10),
                checkOut = LocalDate.of(2024, 1, 14),
            )
            When("새로운 에약을 생성하면") {
                reservationService.addReservation(dto)

                Then("에약 정보, 출금 정보는 레파지토리에 저장 되어야 한다.") {
                    val reservations = reservationRepository.findAll()

                    reservations.size shouldBe 1

                    reservations.first().let {
                        it.name shouldBe "aaa"
                        it.roomNumber shouldBe 101
                        it.checkInDate shouldBe LocalDate.of(2024, 1, 10)
                        it.checkOutDate shouldBe LocalDate.of(2024, 1, 14)
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

        Given("새로운 에약 정보(1월 9일 ~ 1월 10일)가 주어졌을 때(기존 : 1월 10일 ~ 14일)") {
            setUpReservation()

            val dto = ReservationCreateDto(
                name = "aaa",
                roomNumber = 101,
                checkIn = LocalDate.of(2024, 1, 9),
                checkOut = LocalDate.of(2024, 1, 11),
            )

            When("새로운 에약을 생성하면") {
                Then("에러가 발생하고 에약이 생성되지 않아야 한다.") {
                    assertThrows<DuplicatedResourceException> {
                        reservationService.addReservation(dto)
                    }

                    reservationRepository.findAll().size shouldBe 1
                    accountDetailRepository.findAll().size shouldBe 1

                }
            }
        }

        Given("새로운 에약 정보(1월 13일 ~ 1월 15일)가 주어졌을 때(기존 : 1월 10일 ~ 14일)") {
            setUpReservation()

            val dto = ReservationCreateDto(
                name = "aaa",
                roomNumber = 101,
                checkIn = LocalDate.of(2024, 1, 13),
                checkOut = LocalDate.of(2024, 1, 15),
            )

            When("새로운 에약을 생성하면") {
                Then("에러가 발생하고 에약이 생성되지 않아야 한다.") {
                    assertThrows<DuplicatedResourceException> {
                        reservationService.addReservation(dto)
                    }

                    reservationRepository.findAll().size shouldBe 1
                    accountDetailRepository.findAll().size shouldBe 1

                }
            }
        }

        Given("새로운 에약 정보(1월 11일 ~ 1월 13일)가 주어졌을 때(기존 : 1월 10일 ~ 14일)") {
            setUpReservation()

            val dto = ReservationCreateDto(
                name = "aaa",
                roomNumber = 101,
                checkIn = LocalDate.of(2024, 1, 11),
                checkOut = LocalDate.of(2024, 1, 13),
            )

            When("새로운 에약을 생성하면") {
                Then("에러가 발생하고 에약이 생성되지 않아야 한다.") {
                    assertThrows<DuplicatedResourceException> {
                        reservationService.addReservation(dto)
                    }

                    reservationRepository.findAll().size shouldBe 1
                    accountDetailRepository.findAll().size shouldBe 1

                }
            }
        }

        Given("새로운 에약 정보(1월 9일 ~ 1월 15일)가 주어졌을 때(기존 : 1월 10일 ~ 14일)") {
            setUpReservation()

            val dto = ReservationCreateDto(
                name = "aaa",
                roomNumber = 101,
                checkIn = LocalDate.of(2024, 1, 9),
                checkOut = LocalDate.of(2024, 1, 15),
            )

            When("에약을 생성하면") {
                Then("에러가 발생하고 에약이 생성되지 않아야 한다.") {
                    assertThrows<DuplicatedResourceException> {
                        reservationService.addReservation(dto)
                    }

                    reservationRepository.findAll().size shouldBe 1
                    accountDetailRepository.findAll().size shouldBe 1

                }
            }
        }
    }
}