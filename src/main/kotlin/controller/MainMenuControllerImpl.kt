package controller

import enumeration.MainMenuType
class MainMenuControllerImpl : MainMenuController {
    override fun inputMainMenu(): MainMenuType {
        val intCommandOrders = MainMenuType.entries.map { it.commandOrder.toInt() }
        val minCommandOrder = intCommandOrders.min()
        val maxCommandOrder = intCommandOrders.max()

        while(true) {
            print("메뉴: ")
            val commandOrder = readln().trim()
            val menuType = MainMenuType.entries.firstOrNull() { it.commandOrder == commandOrder }
            if (menuType != null) {
                return menuType
            }

            println("입력할 수 있는 명령어는 $minCommandOrder ~ $maxCommandOrder 사이 입니다.")
        }
    }

    override fun printMainMenu() {
        val content = MainMenuType.entries
            .map { " ${it.commandOrder}.${it.description}\t" }
            .fold("") { acc, s -> acc + s }
        println("===============================================[메뉴]===============================================")
        println(content)
        println("===================================================================================================")
    }
}