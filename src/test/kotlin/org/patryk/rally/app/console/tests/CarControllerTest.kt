package org.patryk.rally.app.console.tests

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import org.patryk.rally.app.console.controllers.CarController
import org.patryk.rally.app.console.models.Car

internal class CarControllerTest {

    private val carController = CarController()
    private var dummyCar = Car(
        "",
        "121",
        "testDriver",
        "testNavigator"
    )

    //Run asynchronously in order specified to avoid db connection issue

    @Test
    @Order(1)
    fun add() {
        assertEquals(true, carController.add(dummyCar))
    }

    @Test
    @Order(2)
    fun addFailure() {
        dummyCar.driverName = ""
        assertEquals(false, carController.add(dummyCar))
    }

    @Test
    @Order(3)
    fun search() {
        dummyCar.uid = carController.findCarUID(dummyCar.carNo)
        assertEquals(
            "\n Car Details :" + "\n    Car Number : " + dummyCar.carNo + "\n    Driver Name : " + dummyCar.driverName + "\n    Navigator Name : " + dummyCar.navigatorName,
            carController.search(dummyCar.uid)
        )
    }

    @Test
    @Order(4)
    fun updateFail() {
        assertEquals(false, carController.update(dummyCar))
    }

    @Test
    @Order(5)
    fun update() {
        dummyCar.uid = carController.findCarUID(dummyCar.carNo)
        dummyCar.driverName = "updatedTestDriver"
        assertEquals(true, carController.update(dummyCar))

    }

    @Test
    @Order(6)
    fun deleteFail() {
        dummyCar.uid = ""
        assertEquals(false, carController.delete(dummyCar))

    }

    @Test
    @Order(7)
    fun delete() {
        dummyCar.uid = carController.findCarUID(dummyCar.carNo)
        assertEquals(true, carController.delete(dummyCar))
    }

}