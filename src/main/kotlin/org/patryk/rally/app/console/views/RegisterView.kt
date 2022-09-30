package org.patryk.rally.app.console.views

import javafx.geometry.Pos
import javafx.scene.control.TextField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.models.UserModel
import tornadofx.*

class RegisterView : View("Register User") {
    private var createUserName: TextField = TextField()
    private var createUserPassword: TextField = TextField()
    private var createUserPasswordRepeat: TextField = TextField()

    private var userController = UserController()

    override val root = vbox {
        form {
            vbox(20, Pos.TOP_RIGHT) {
                buttonbar {
                    button("Return to Login") {
                        action {
                            find(RegisterView::class).replaceWith(
                                MainView::class,
                                sizeToScene = true,
                                centerOnScreen = true
                            )
                        }
                    }
                }
            }
            hbox(20) {
                fieldset("Register User") {
                    hbox(20) {
                        vbox {
                            field("User Name") { createUserName = textfield() }
                            field("Password") { createUserPassword = textfield() }
                            field("Repeat Password") { createUserPasswordRepeat = textfield() }
                            buttonbar {
                                button("Create") {
                                    action {
                                        if (createUserPassword.text != createUserPasswordRepeat.text) {
                                            createUserPassword.text = "NoMatch"
                                        }
                                        val newUser = UserModel(
                                            createUserName.text,
                                            createUserPassword.text,
                                            0
                                        )
                                        if (userController.add(newUser)) {
                                            find(RegisterView::class).replaceWith(
                                                LoginView::class,
                                                sizeToScene = true,
                                                centerOnScreen = true
                                            )
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }
    }
}