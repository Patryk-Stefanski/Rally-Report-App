package org.patryk.rally.app.console.views

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.PasswordField
import org.patryk.rally.app.console.controllers.UserController
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser
import org.patryk.rally.app.console.models.*
import tornadofx.*
import javax.swing.text.Style

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
                            if (thisUser.admin == 0) {
                                find(UserManagementView::class).replaceWith(
                                    MainView::class, sizeToScene = true, centerOnScreen = true
                                )
                            } else {
                                find(UserManagementView::class).replaceWith(
                                    AdminMainView::class, sizeToScene = true, centerOnScreen = true
                                )
                            }
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
                                            oldPassword.text,
                                            0
                                        )
                                        if ( userController.update(newUser , newPassword.text)) {
                                            alert(Alert.AlertType.CONFIRMATION, "Success", "User has been updated")
                                        } else {
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Failed to update user",
                                                "Make sure the correct password was entered and that  new ones match"
                                            )
                                        }

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
                                        if ( userController.delete(newUser)) {
                                            alert(Alert.AlertType.CONFIRMATION, "Success", "User has been deleted")
                                            find(UserManagementView::class).replaceWith(
                                                LoginView::class,
                                                sizeToScene = true,
                                                centerOnScreen = true
                                            )
                                        } else {
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Failed to delete user",
                                                "Make sure the correct password was entered and that it matches"
                                            )
                                        }
                                    }



                                }
                            }
                        }
                    }
                    vbox(20) {
                        buttonbar {
                            button("Log Out") {
                                action {
                                    thisUser.username = ""
                                    thisUser.password = ""
                                    thisUser.admin = 0
                                    find(UserManagementView::class).replaceWith(
                                        LoginView::class,
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
    }
}