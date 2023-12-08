package reservation.dto

import reservation.entity.AccountDetail
import reservation.enumeration.AccountDetailHistoryType

data class AccountDetailReadDto(
    val id: Long,
    val name: String,
    val amount: Int,
    val description: String,
    val typeKoreanName: String,
) {
    companion object {
        fun from(
            accountDetail: AccountDetail
        ): AccountDetailReadDto {
            val typeKoreanName = when (accountDetail.type) {
                AccountDetailHistoryType.DEPOSIT -> "입금"
                AccountDetailHistoryType.WITHDRAWAL -> "출금"
            }

            return AccountDetailReadDto(
                id = accountDetail.id,
                name = accountDetail.name,
                amount = accountDetail.amount,
                description = accountDetail.description,
                typeKoreanName = typeKoreanName,
            )
        }
    }
}