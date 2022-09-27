package org.patryk.rally.app.console.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastLocationId = 0L


internal fun getLocationId(): Long {
    return lastLocationId++
}


class LocationMemStore : LocationStore {

    val locations = ArrayList<LocationModel>()

    override fun findAll(): List<LocationModel> {
        return locations
    }

    override fun findOne(id: Long) : LocationModel? {
        var foundLocation: LocationModel? = locations.find { p -> p.id == id }
        return foundLocation
    }

    override fun create(location: LocationModel) {
        location.id = getLocationId()
        locations.add(location)
        logAll()
    }

    override fun update(location: LocationModel) {
        var foundLocation = findOne(location.id!!)
        if (foundLocation != null) {
            foundLocation.stage = location.stage
            foundLocation.corner = location.corner

        }
    }

    internal fun logAll() {
        locations.forEach { logger.info("$it") }
    }
}