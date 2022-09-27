package org.patryk.rally.app.console.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastCarId = 0L

internal fun getCarId(): Long {
    return lastCarId++
}

class CarMemStore : CarStore {

    val cars = ArrayList<CarModel>()

    override fun findAll(): List<CarModel> {
        return cars
    }

    override fun findOne(id: Long) : CarModel? {
        var foundCar: CarModel? = cars.find { p -> p.id == id }
        return foundCar
    }

    override fun create(car: CarModel) {
        car.id = getCarId()
        cars.add(car)
        logAll()
    }

    override fun update(car: CarModel) {
        var foundCar = findOne(car.id!!)
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