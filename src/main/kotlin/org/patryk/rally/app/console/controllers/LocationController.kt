package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.LocationMemStore
import org.patryk.rally.app.console.models.LocationModel
import org.patryk.rally.app.console.views.LocationsView

class LocationController {
    val locations = LocationMemStore()
    val locationView = LocationsView()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Location Menu" }
    }

    fun start() {

        var input: Int

        do {
            input = locationView.locationMenu()
            when (input) {
                1 -> add()
                2 -> update()
                3 -> list()
                4 -> search()
                0 -> returnMainMenu()
                -99 -> dummyData()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Rally Report Console App" }
    }

    fun add() {
        val location = LocationModel()

        if (locationView.addLocationData(location))
            locations.create(location)
        else
            logger.info("Location Not Added")
    }

    fun list() {
        locationView.listLocations(locations)
    }

    fun update() {

        locationView.listLocations(locations)
        val searchId = locationView.getId()
        val location = search(searchId)

        if (location != null) {
            if (locationView.updateLocationData(location)) {
                locations.update(location)
                locationView.showLocation(location)
                logger.info("Location Updated : [ $location ]")
            } else
                logger.info("Location Not Updated")
        } else
            println("Location Not Updated...")
    }

    fun search() {
        val location = search(locationView.getId())!!
        locationView.showLocation(location)
    }

    fun search(id: Long): LocationModel? {
        val foundLocation = locations.findOne(id)
        return foundLocation
    }

    fun dummyData() {
        locations.create(LocationModel( id = 0 , stage = 1 , corner = 1))
        locations.create(LocationModel( id = 1 , stage = 1 , corner = 2))
        locations.create(LocationModel( id = 2 , stage = 2 , corner = 1))
        locations.create(LocationModel( id = 3 , stage = 2 , corner = 2))
        locations.create(LocationModel( id = 4 , stage = 2 , corner = 3))
    }

    fun returnMainMenu() {
        MainController().start()
    }
}