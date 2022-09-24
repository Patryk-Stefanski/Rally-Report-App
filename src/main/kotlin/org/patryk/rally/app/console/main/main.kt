package org.patryk.rally.app.console.main

import  mu.KotlinLogging
import org.patryk.rally.app.console.controllers.MainController

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
 MainController().start()
}

