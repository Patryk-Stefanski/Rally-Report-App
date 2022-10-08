package org.patryk.rally.app.console.views
import org.patryk.rally.app.console.controllers.UserController.Companion.thisUser

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.patryk.rally.app.console.controllers.CarController
import org.patryk.rally.app.console.models.Car
import tornadofx.*


class CarView : View("Car View") {
    private var createCarNo: TextField = TextField()
    private var createDriverName: TextField = TextField()
    private var createNavigatorName: TextField = TextField()
    private var updateCarUID: TextField = TextField()
    private var updateCarNo: TextField = TextField()
    private var updateDriverName: TextField = TextField()
    private var updateNavigatorName: TextField = TextField()
    private var deleteCarUID: TextField = TextField()
    private var listOfCars: TextArea = TextArea()

    private var carController = CarController()


    private fun refreshTextArea() {
        val carList = carController.list().toString()
        listOfCars.clear()
        listOfCars.appendText(carList.substring(1, carList.length - 1))
    }


    override val root = vbox {
        form {
            vbox(20, Pos.TOP_RIGHT) {
                buttonbar {
                    button("Return to Main Menu") {
                        action {
                            if (thisUser.admin == 0) {
                                find(CarView::class).replaceWith(
                                    MainView::class, sizeToScene = true, centerOnScreen = true
                                )
                            } else {
                                find(CarView::class).replaceWith(
                                    AdminMainView::class, sizeToScene = true, centerOnScreen = true
                                )
                            }
                        }

                    }
                }
            }
            fieldset("List of Cars") {
                listOfCars = textarea {
                    val carList = carController.list().toString()
                    text = carList.substring(1, carList.length - 1)
                    prefRowCount = 5
                    vgrow = Priority.ALWAYS
                }
                hbox(20) {
                    fieldset("Create Car") {
                        hbox(20) {
                            vbox {
                                field("Car Number") { createCarNo = textfield() }
                                field("Driver Name") { createDriverName = textfield() }
                                field("Navigator Name") { createNavigatorName = textfield() }
                                buttonbar {
                                    button("Create") {
                                        action {
                                            val newCarModel = Car(
                                                "",
                                                createCarNo.text,
                                                createDriverName.text,
                                                createNavigatorName.text
                                            )
                                            if (carController.add(newCarModel)) {
                                                refreshTextArea()
                                                alert(
                                                    Alert.AlertType.CONFIRMATION,
                                                    "Success",
                                                    "New car has been created"
                                                )
                                            } else {
                                                alert(
                                                    Alert.AlertType.INFORMATION,
                                                    "Failed to create new car",
                                                    "Make sure all the details are filled in"
                                                )
                                            }
                                        }

                                    }
                                }

                            }
                        }
                        fieldset("Update Car") {
                            hbox(20) {
                                vbox {
                                    field("Car UID") { updateCarUID = textfield() }
                                    field("New Car Number") { updateCarNo = textfield() }
                                    field("New Driver Name") { updateDriverName = textfield() }
                                    field("New Navigator Name") { updateNavigatorName = textfield() }
                                    buttonbar {
                                        button("Update") {
                                            action {
                                                val newCarModel = Car(
                                                    updateCarUID.text,
                                                    updateCarNo.text,
                                                    updateDriverName.text,
                                                    updateNavigatorName.text
                                                )
                                                if (carController.update(newCarModel)) {
                                                    refreshTextArea()
                                                    alert(
                                                        Alert.AlertType.CONFIRMATION,
                                                        "Success",
                                                        "Car has been updated"
                                                    )
                                                } else {
                                                    alert(
                                                        Alert.AlertType.INFORMATION,
                                                        "Failed to update car",
                                                        "Make sure all the details are filled in"
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            fieldset("Delete Car") {
                                hbox(20) {
                                    vbox {
                                        field("Car UID") { deleteCarUID = textfield() }
                                        buttonbar {
                                            button("Delete") {
                                                action {
                                                    val newCarModel = Car(deleteCarUID.text, "", "", "")
                                                    if (carController.delete(newCarModel)) {
                                                        refreshTextArea()
                                                        alert(
                                                            Alert.AlertType.CONFIRMATION,
                                                            "Success",
                                                            "Car has been deleted"
                                                        )
                                                    } else {
                                                        alert(
                                                            Alert.AlertType.INFORMATION,
                                                            "Failed to delete car",
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
        }
    }
}