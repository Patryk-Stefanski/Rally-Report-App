package org.patryk.rally.app.console.views
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser

import javafx.scene.control.Alert
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.models.User
import tornadofx.*

class LoginView : View("Login") {
    private var loginUsername: TextField = TextField()
    private var loginPassword: PasswordField = PasswordField()

    private val userController = UserController()

    override val root = vbox {
        form {
            hbox(20) {
                fieldset("Login") {
                    hbox(20) {
                        vbox(20) {
                            field("User Name") { loginUsername = textfield() }
                            field("Password") { loginPassword = passwordfield() }
                            buttonbar {
                                button("Login") {
                                    action {
                                        val newUser = User(
                                            loginUsername.text,
                                            loginPassword.text,
                                            0
                                        )
                                        if (userController.login(newUser)) {
                                            loginUsername.text = ""
                                            loginPassword.text = ""
                                        } else {
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Incorrect Password",
                                                "Username and password combination is incorrect"
                                            )
                                        }

                                        if (thisUser.admin == 0){
                                            find(LoginView::class).replaceWith(
                                                MainView::class,
                                                sizeToScene = true,
                                                centerOnScreen = true
                                            )
                                        } else {
                                            find(LoginView::class).replaceWith(
                                                AdminMainView::class,
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