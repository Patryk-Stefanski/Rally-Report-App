package org.patryk.rally.app.console.main

import javafx.application.Application
import org.patryk.rally.app.console.views.*
import tornadofx.App
import tornadofx.launch


class Main : App(LoginView::class) {
}

fun main(args: Array<String>) = launch<Main>()

