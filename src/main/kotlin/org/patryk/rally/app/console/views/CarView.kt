package org.patryk.rally.app.console.views

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.patryk.rally.app.console.controllers.CarController
import org.patryk.rally.app.console.models.CarModel
import tornadofx.*


class CarView : View("Car View") {
    private var createCarNo: TextField = TextField()
    private var createDriverName:TextField = TextField()
    private var createNavigatorName:TextField = TextField()
    private var updateCarUID:TextField = TextField()
    private var updateCarNo: TextField = TextField()
    private var updateDriverName:TextField = TextField()
    private var updateNavigatorName:TextField = TextField()
    private var deleteCarUID:TextField = TextField()
    private var listOfCars:TextArea = TextArea()

    var carController = CarController()




    override val root = vbox {
        form {
            fieldset("List of Cars") {
                listOfCars = textarea {
                    text = carController.list()
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
                                            val newCarModel = CarModel(
                                                "",
                                                createCarNo.text,
                                                createDriverName.text,
                                                createNavigatorName.text
                                            )
                                            carController.add(newCarModel)
                                            listOfCars.clear()
                                            listOfCars.appendText(carController.list())
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
                                            val newCarModel = CarModel(
                                                updateCarUID.text,
                                                updateCarNo.text,
                                                updateDriverName.text,
                                                updateNavigatorName.text
                                            )
                                            carController.update(newCarModel)
                                            listOfCars.clear()
                                            listOfCars.appendText(carController.list())
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
                                            val newCarModel = CarModel(deleteCarUID.text, "", "", "")
                                            carController.delete(newCarModel)
                                            listOfCars.clear()
                                            listOfCars.appendText(carController.list())
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