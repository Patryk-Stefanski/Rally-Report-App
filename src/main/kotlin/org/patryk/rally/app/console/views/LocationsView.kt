package org.patryk.rally.app.console.views

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.patryk.rally.app.console.controllers.LocationController
import org.patryk.rally.app.console.models.Location
import tornadofx.*

class LocationsView : View("Location View") {
    private var createLocationStage: TextField = TextField()
    private var createLocationCorner: TextField = TextField()
    private var updateLocationUID: TextField = TextField()
    private var updateLocationStage: TextField = TextField()
    private var updateLocationCorner: TextField = TextField()
    private var deleteLocationUID: TextField = TextField()
    private var listOfLocations: TextArea = TextArea()

    private var locationController = LocationController()

    private fun refreshTextArea() {
        val locationList = locationController.list().toString()
        listOfLocations.clear()
        listOfLocations.appendText(locationList.substring(1, locationList.length - 1))
    }


    override val root = vbox {
        form {
            vbox(20, Pos.TOP_RIGHT) {
                buttonbar {
                    button("Return to Main Menu") {
                        action {
                            find(LocationsView::class).replaceWith(
                                MainView::class, sizeToScene = true, centerOnScreen = true
                            )
                        }
                    }
                }
            }
            fieldset("List of Locations") {
                listOfLocations = textarea {
                    val locations = locationController.list().toString()
                    text = locations.substring(1, locations.length - 1)
                    prefRowCount = 5
                    vgrow = Priority.ALWAYS
                }
                hbox(20) {
                    fieldset("Create Location") {
                        hbox(20) {
                            vbox {
                                field("Stage Number") { createLocationStage = textfield() }
                                field("Corner Number") { createLocationCorner = textfield() }
                                buttonbar {
                                    button("Create") {
                                        action {
                                            val newLocationModel = Location(
                                                "",
                                                createLocationStage.text,
                                                createLocationCorner.text,
                                            )
                                            if (locationController.add(newLocationModel)) {
                                                refreshTextArea()
                                                alert(
                                                    Alert.AlertType.CONFIRMATION,
                                                    "Success",
                                                    "New Location has been created"
                                                )
                                            } else {
                                                alert(
                                                    Alert.AlertType.INFORMATION,
                                                    "Failed to create new Location",
                                                    "Make sure that all the details are filled in"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    fieldset("Update Location") {
                        hbox(20) {
                            vbox {
                                field("Location UID") { updateLocationUID = textfield() }
                                field("New Stage Number") { updateLocationStage = textfield() }
                                field("New Corner Number") { updateLocationCorner = textfield() }
                                buttonbar {
                                    button("Update") {
                                        action {
                                            val newLocationModel = Location(
                                                updateLocationUID.text,
                                                updateLocationStage.text,
                                                updateLocationCorner.text,
                                            )
                                            if (locationController.update(newLocationModel)) {
                                                alert(
                                                    Alert.AlertType.CONFIRMATION,
                                                    "Success",
                                                    "Location has been Updated"
                                                )
                                                refreshTextArea()
                                            } else {
                                                alert(
                                                    Alert.AlertType.INFORMATION,
                                                    "Failed to update location",
                                                    "Make sure all the details are filled in"
                                                )
                                            }

                                        }
                                    }
                                }
                            }

                        }
                    }
                    fieldset("Delete Location") {
                        hbox(20) {
                            vbox {
                                field("Location UID") { deleteLocationUID = textfield() }
                                buttonbar {
                                    button("Delete") {
                                        action {
                                            val newLocationModel = Location(deleteLocationUID.text, "", "")

                                            if (locationController.delete(newLocationModel)) {
                                                refreshTextArea()
                                                alert(
                                                    Alert.AlertType.CONFIRMATION,
                                                    "Success",
                                                    "Location has been deleted"
                                                )
                                            } else {
                                                alert(
                                                    Alert.AlertType.INFORMATION,
                                                    "Failed to delete location",
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