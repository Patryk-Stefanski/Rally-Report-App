package org.patryk.rally.app.console.views

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.patryk.rally.app.console.controllers.CarController
import org.patryk.rally.app.console.controllers.LocationController
import org.patryk.rally.app.console.controllers.PostController
import org.patryk.rally.app.console.models.Post
import tornadofx.*
import java.time.LocalDate
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser


class PostView : View("Post View") {
    private var chosenCar: ComboBox<String> = ComboBox()
    private var chosenLocation: ComboBox<String> = ComboBox()
    private var titleVal: TextField = TextField()
    private var chosenCarUpdate: ComboBox<String> = ComboBox()
    private var chosenLocationUpdate: ComboBox<String> = ComboBox()
    private var titleUpdate: TextField = TextField()
    private var postUidUpdate: TextField = TextField()
    private var postUidDelete: TextField = TextField()
    private var listOfPosts: TextArea = TextArea()

    private var postController = PostController()
    private var carController = CarController()
    private var locationController = LocationController()
    private val dateProperty = SimpleObjectProperty<LocalDate>()

    private fun refreshTextArea() {
        val postsList = postController.list().toString()
        listOfPosts.clear()
        listOfPosts.appendText(postsList.substring(1, postsList.length - 1))
    }

    override val root = vbox {
        form {
            hbox(20, Pos.TOP_RIGHT) {
                buttonbar {
                    button("Return to Main Menu") {
                        action {
                            find(PostView::class).replaceWith(
                                MainView::class,
                                sizeToScene = true,
                                centerOnScreen = true
                            )
                        }

                    }
                }
                buttonbar {
                    button("Reload Combobox") {
                        action {
                            chosenCar.items.setAll(carController.list())
                            chosenLocation.items.setAll(locationController.list())
                            chosenCarUpdate.items.setAll(carController.list())
                            chosenCarUpdate.items.setAll(locationController.list())
                        }

                    }
                }
            }
            fieldset("Posts") {
                listOfPosts = textarea {
                    val postList = postController.list().toString()
                    text = postList.substring(1, postList.length - 1)
                    prefRowCount = 5
                    vgrow = Priority.ALWAYS
                }
                hbox(20) {
                    fieldset("Create Post") {
                        hbox(20) {
                            vbox {
                                field("Car") {
                                    chosenCar = combobox {
                                        items = carController.list()
                                    }
                                }
                                field("Location") {
                                    chosenLocation = combobox {
                                        items = locationController.list()
                                    }
                                }
                                field("Title") {
                                    titleVal = textfield()
                                }
                                field("Date") {
                                    datepicker(dateProperty) {
                                        value = LocalDate.now()
                                    }
                                }
                                buttonbar {
                                    button("Create") {
                                        action {
                                            if (chosenCar.value == null) {
                                                chosenCar.value = ""
                                            }
                                            if (chosenLocation.value == null) {
                                                chosenLocation.value = ""
                                            }
                                            val newPost = Post(
                                                "",
                                                thisUser.username,
                                                chosenCar.value,
                                                chosenLocation.value,
                                                titleVal.text,
                                                dateProperty.value,
                                                ""
                                            )
                                            if (postController.add(newPost)) {
                                                alert(
                                                    Alert.AlertType.CONFIRMATION,
                                                    "Success",
                                                    "New post has been created"
                                                )
                                                refreshTextArea()
                                            } else {
                                                alert(
                                                    Alert.AlertType.INFORMATION,
                                                    "Failed to create post",
                                                    "Make sure all the details are filled in"
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
                fieldset("Update Post") {
                    hbox(20) {
                        vbox {
                            field("Post UID") { postUidUpdate = textfield() }
                            field("Car") {
                                chosenCarUpdate = combobox {
                                    items = carController.list()
                                }
                            }
                            field("Location") {
                                chosenLocationUpdate = combobox {
                                    items = locationController.list()
                                }
                            }
                            field("Title") {
                                titleUpdate = textfield()
                            }
                            field("Date") {
                                datepicker(dateProperty) {
                                    value = LocalDate.now()
                                }
                            }
                            buttonbar {
                                button("Update") {
                                    if (chosenCarUpdate.value == null) {
                                        chosenCarUpdate.value = ""
                                    }
                                    if (chosenLocationUpdate.value == null) {
                                        chosenLocationUpdate.value = ""
                                    }
                                    action {
                                        val newPost = Post(
                                            postUidUpdate.text,
                                            thisUser.username,
                                            chosenCarUpdate.value,
                                            chosenLocationUpdate.value,
                                            titleUpdate.text,
                                            dateProperty.value,
                                            ""
                                        )
                                        if (postController.update(newPost)) {
                                            alert(Alert.AlertType.CONFIRMATION, "Success", "Post has been Updated")
                                            refreshTextArea()
                                        } else {
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Failed to update post",
                                                "Make sure all the details are filled in"
                                            )
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
                fieldset("Delete Post") {
                    hbox(20) {
                        vbox {
                            field("Post UID") { postUidDelete = textfield() }
                            buttonbar {
                                button("Delete") {
                                    action {
                                        if (postController.delete(postUidDelete.text)) {
                                            refreshTextArea()
                                            alert(Alert.AlertType.CONFIRMATION, "Success", "Post has been deleted")
                                        } else {
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Failed to delete post",
                                                "Make sure the correct UID is supplied"
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
