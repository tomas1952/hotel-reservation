package reservation.entity

import reservation.enumeration.AccountDetailHistoryType

class AccountDetail(
    val name: String,
    val amount: Int,
    val type: AccountDetailHistoryType,
) {
    var id: Long = -1L
}