package org.patryk.rally.app.console.views

import javafx.geometry.Pos
import javafx.scene.control.TextField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser
import org.patryk.rally.app.console.models.*
import tornadofx.*

class UserManagementView : View("User Management View") {
    private var oldPassword: TextField = TextField()
    private var newPassword: TextField = TextField()
    private var repeatNewPassword: TextField = TextField()
    private var deletePassword: TextField = TextField()
    private var deletePasswordConfirm: TextField = TextField()

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
                            field("Current Password") { oldPassword = textfield() }
                            field("New Password") { newPassword = textfield() }
                            field("Repeat New Password") { repeatNewPassword = textfield() }
                            buttonbar {
                                button("Apply Changes") {
                                    action {
                                        if (newPassword.text != repeatNewPassword.text) {
                                            newPassword.text = "NoMatch"
                                        }
                                        val newUser = UserModel(
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
                            field("Password") { deletePassword = textfield() }
                            field("Confirm Password") { deletePasswordConfirm = textfield() }
                            buttonbar {
                                button("Delete") {
                                    action {
                                        if (deletePassword.text != deletePasswordConfirm.text) {
                                            deletePassword.text = "NoMatch"
                                        }
                                        val newUser = UserModel(
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