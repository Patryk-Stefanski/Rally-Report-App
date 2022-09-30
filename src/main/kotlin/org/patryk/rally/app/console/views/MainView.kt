package org.patryk.rally.app.console.views

import tornadofx.*
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser


class MainView : View("Main Menu") {
    override val root = vbox(20) {
        form {
            buttonbar {
                button("Posts") {
                    action {
                    }
                }
                style {
                    padding = box(10.px)
                }
            }
            buttonbar {
                button("User Management") {
                    action {
                        find(MainView::class).replaceWith(
                            UserManagementView::class,
                            sizeToScene = true,
                            centerOnScreen = true
                        )
                    }
                }
                style {
                    padding = box(10.px)
                }
            }
            if (thisUser.admin == 1) {
                buttonbar {
                    button("Manage Cars") {
                        action {
                            find(MainView::class).replaceWith(CarView::class, sizeToScene = true, centerOnScreen = true)
                        }
                    }
                    style {
                        padding = box(10.px)
                    }
                }
                buttonbar {
                    button("Manage Locations") {
                        action {
                            find(MainView::class).replaceWith(
                                LocationsView::class,
                                sizeToScene = true,
                                centerOnScreen = true
                            )
                        }
                    }
                    style {
                        padding = box(10.px)
                    }
                }
            }
        }
    }
}