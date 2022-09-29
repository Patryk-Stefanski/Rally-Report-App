package org.patryk.rally.app.console.views

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.patryk.rally.app.console.controllers.LocationController
git import org.patryk.rally.app.console.models.LocationModel
import tornadofx.*

class LocationsView : View("Location View"){
    private var createLocationStage: TextField = TextField()
    private var createLocationCorner: TextField = TextField()
    private var updateLocationUID: TextField = TextField()
    private var updateLocationStage: TextField = TextField()
    private var updateLocationCorner: TextField = TextField()
    private var deleteLocationUID: TextField = TextField()
    private var listOfLocations: TextArea = TextArea()

    var locationController = LocationController()


    override val root = vbox {
        form {
            fieldset("List of Locations") {
                listOfLocations = textarea {
                    text = locationController.list()
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
                                            val newLocationModel = LocationModel(
                                                "",
                                                createLocationStage.text,
                                                createLocationCorner.text,
                                            )
                                            locationController.add(newLocationModel)
                                            listOfLocations.clear()
                                            listOfLocations.appendText(locationController.list())
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
                                            val newLocationModel = LocationModel(
                                                updateLocationUID.text,
                                                updateLocationStage.text,
                                                updateLocationCorner.text,
                                            )
                                            locationController.update(newLocationModel)
                                            listOfLocations.clear()
                                            listOfLocations.appendText(locationController.list())
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
                                            val newLocationModel = LocationModel(deleteLocationUID.text, "", "",)
                                            locationController.delete(newLocationModel)
                                            listOfLocations.clear()
                                            listOfLocations.appendText(locationController.list())
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