package main

import common.enumeration.MainMenuType

interface MainMenuController {
    fun inputMainMenu(): MainMenuType
    fun printMainMenu()
}