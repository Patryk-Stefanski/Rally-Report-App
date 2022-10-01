package org.patryk.rally.app.console.views

import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser
import org.patryk.rally.app.console.models.*
import tornadofx.*

class UserManagementView : View("User Management View") {
    private var oldPassword: PasswordField = PasswordField()
    private var newPassword: PasswordField = PasswordField()
    private var repeatNewPassword: PasswordField = PasswordField()
    private var deletePassword: PasswordField = PasswordField()
    private var deletePasswordConfirm: PasswordField = PasswordField()

    private var userController = UserController()

    override val root = vbox {
        form {
            vbox(20, Pos.TOP_RIGHT) {
                buttonbar {
                    button("Return to Main Menu") {
                        action {
                            find(UserManagementView::class).replaceWith(
                                MainView::class,
                                sizeToScene = true,
                                centerOnScreen = true
                            )
                        }
                    }
                }
            }
            hbox(20) {
                fieldset("Change Password") {
                    hbox(20) {
                        vbox {
                            field("Current Password") { oldPassword = passwordfield() }
                            field("New Password") { newPassword = passwordfield() }
                            field("Repeat New Password") { repeatNewPassword = passwordfield() }
                            buttonbar {
                                button("Apply Changes") {
                                    action {
                                        if (newPassword.text != repeatNewPassword.text) {
                                            newPassword.text = "NoMatch"
                                        }
                                        val newUser = User(
                                            thisUser.username,
                                            newPassword.text,
                                            0
                                        )
                                        userController.update(newUser)
                                    }
                                }

                            }
                        }

                    }
                }
                fieldset("Delete User") {
                    hbox(20) {
                        vbox {
                            field("Password") { deletePassword = passwordfield() }
                            field("Confirm Password") { deletePasswordConfirm = passwordfield() }
                            buttonbar {
                                button("Delete") {
                                    action {
                                        if (deletePassword.text != deletePasswordConfirm.text) {
                                            deletePassword.text = "NoMatch"
                                        }
                                        val newUser = User(
                                            thisUser.username,
                                            deletePassword.text,
                                            0
                                        )
                                        userController.delete(newUser)
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