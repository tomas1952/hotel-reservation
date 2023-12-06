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
fun main(args: Array<String>) {
    var lastedInput = 0
    while(true) {
        println("호텔예약 프로그램 입니다.")
        println("===============================================[메뉴]===============================================")
        println("1.방예약    2.예약목록 출력    3.예약목로 (정렬) 출력    4.시스템 종료    5.금액 입금-출금 내역 목록 출력    6.예약 변경")
        println("===================================================================================================")

        val command = inputAvailableMenu()
        when(command) {
            4 -> {
                println("시스템을 종료 합니다.")
                break;
            }
            else -> {
                println("TBD")
            }
        }
    }

}