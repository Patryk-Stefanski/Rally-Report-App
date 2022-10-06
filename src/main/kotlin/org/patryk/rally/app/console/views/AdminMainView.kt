package org.patryk.rally.app.console.views

import org.patryk.rally.app.console.controllers.UserController
import tornadofx.*

class AdminMainView : View("Admin Main Menu") {
    override val root = vbox(20) {
        form {
            buttonbar {
                button("Posts") {
                    action {
                        find(AdminMainView::class).replaceWith(
                            PostView::class,
                            sizeToScene = true,
                            centerOnScreen = true
                        )
                    }
                }
                style {
                    padding = box(10.px)
                }
            }
            buttonbar {
                button("User Management") {
                    action {
                        find(AdminMainView::class).replaceWith(
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
            buttonbar {
                button("Manage Cars") {
                    action {
                        find(AdminMainView::class).replaceWith(CarView::class, sizeToScene = true, centerOnScreen = true)
                    }
                }
                style {
                    padding = box(10.px)
                }
            }
            buttonbar {
                button("Manage Locations") {
                    action {
                        find(AdminMainView::class).replaceWith(
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
