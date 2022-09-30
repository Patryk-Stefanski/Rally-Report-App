package org.patryk.rally.app.console.views

import javafx.scene.control.TextField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.models.UserModel
import tornadofx.*

class LoginView : View("Login") {
    private var loginUsername: TextField = TextField()
    private var loginPassword: TextField = TextField()

    private val userController = UserController()

    override val root = vbox {
        form {
            hbox(20) {
                fieldset("Login") {
                    hbox(20) {
                        vbox(20) {
                            field("User Name") { loginUsername = textfield() }
                            field("Password") { loginPassword = textfield() }
                            buttonbar {
                                button("Login") {
                                    action {
                                        val newUser = UserModel(
                                            loginUsername.text,
                                            loginPassword.text,
                                            0
                                        )
                                        if (userController.login(newUser)) {
                                            find(LoginView::class).replaceWith(
                                                MainView::class,
                                                sizeToScene = true,
                                                centerOnScreen = true
                                            )
                                        }
                                    }
                                }
                            }
                            buttonbar {
                                button("Register User") {
                                    action {
                                        find(LoginView::class).replaceWith(
                                            RegisterView::class,
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