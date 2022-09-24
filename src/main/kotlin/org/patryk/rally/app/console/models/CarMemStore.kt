package org.patryk.rally.app.console.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class CarMemStore : CarStore {

    val cars = ArrayList<CarModel>()

    override fun findAll(): List<CarModel> {
        return cars
    }

    override fun findOne(id: Long) : CarModel? {
        var foundCar: CarModel? = cars.find { p -> p.carId == id }
        return foundCar
    }

    override fun create(car: CarModel) {
        car.carId = getId()
        cars.add(car)
        logAll()
    }

    override fun update(car: CarModel) {
        var foundCar = findOne(car.carId!!)
        if (foundCar != null) {
            foundCar.carNo = car.carNo
            foundCar.driverName = car.driverName
            foundCar.navigatorName = car.navigatorName
        }
    }

    internal fun logAll() {
        cars.forEach { logger.info("$it") }
    }
}