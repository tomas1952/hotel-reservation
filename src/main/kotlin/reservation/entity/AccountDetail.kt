package reservation.entity

import reservation.enumeration.AccountDetailType

class AccountDetail(
    val name: String,
    val description: String,
    val amount: Int,
    val type: AccountDetailType,
) {
    var id: Long = -1L
}