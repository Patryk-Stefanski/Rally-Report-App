package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.views.MainView


class MainController {

    val mainView = MainView()
    val logger = KotlinLogging.logger {}

    init {
        println("Rally Report App - v0.0.2")
        logger.info { "Launching Rally Report Console" }
    }

    fun start() {

        var input: Int

        do {
            input = mainView.mainMenu()
            when(input) {
                1 -> println("TO DO LOGIN")
                2 -> userMenu()
                3 -> postMenu()
                4 -> locationMenu()
                5 -> carMenu()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Rally Report Console App" }
    }

    fun carMenu(){
        CarController().start()
    }

    fun locationMenu(){
        LocationController().start()
    }

    fun userMenu(){
        UserController().start()
    }

    fun postMenu(){
        PostController().start()
    }


}