package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.CarModel
import org.patryk.rally.app.console.models.CarMemStore
import org.patryk.rally.app.console.views.CarView


class CarController {
    val cars = CarMemStore()
    val carView = CarView()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Car Menu" }
    }

    fun start() {

        var input: Int

        do {
            input = carView.carMenu()
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
        val aCar = CarModel()

        if (carView.addCarData(aCar))
            cars.create(aCar)
        else
            logger.info("Car Not Added")
    }

    fun list() {
        carView.listCars(cars)
    }

    fun update() {

        carView.listCars(cars)
        val searchId = carView.getId()
        val aCar = search(searchId)

        if (aCar != null) {
            if (carView.updateCarData(aCar)) {
                cars.update(aCar)
                carView.showCar(aCar)
                logger.info("Car Updated : [ $aCar ]")
            } else
                logger.info("Car Not Updated")
        } else
            println("Car Not Updated...")
    }

    fun search() {
        val aCar = search(carView.getId())!!
        carView.showCar(aCar)
    }

    fun search(id: Long): CarModel? {
        val foundCar = cars.findOne(id)
        return foundCar
    }

    fun dummyData() {
        cars.create(CarModel(carId = 0, carNo = 1, driverName = "Josh Moffet", navigatorName = "Andy Hayes"))
        cars.create(CarModel(carId = 1, carNo = 2, driverName = "Craig Breen", navigatorName = "Paul Nagle"))
        cars.create(CarModel(carId = 2, carNo = 3, driverName = "James Stafford", navigatorName = "Thomas Scallan"))
    }

    fun returnMainMenu() {
        MainController().start()
    }

}