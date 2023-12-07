package enumeration

enum class MainMenuType(
    val description: String,
    val commandOrder: String,
) {
    RESERVATION("방예약", "1"),
    RESERVATION_LIST("에약목록 출력", "2"),
    SORTED_RESERVATION_LIST("예약목록 출력(정렬)", "3"),
    QUIT("시스템 종료", "4"),
    DEPOSIT_WITHDRAWAL_HISTORY_LIST("금액 입금-출금 내역 목록 출력", "5"),
    RESERVATION_CHANGES("예약 변경", "6"),
    ;
}