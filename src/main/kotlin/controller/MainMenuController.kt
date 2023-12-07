package controller

import enumeration.MainMenuType

interface MainMenuController {
    fun inputMainMenu(): MainMenuType
    fun printMainMenu()
}