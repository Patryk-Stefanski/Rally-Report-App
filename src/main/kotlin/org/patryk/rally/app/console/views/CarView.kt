package org.patryk.rally.app.console.views

import org.patryk.rally.app.console.models.CarMemStore
import org.patryk.rally.app.console.models.CarModel

class CarView {

    fun carMenu() : Int {

        var option : Int
        var input: String?

        println("CAR MENU")
        println(" 1. Add Car")
        println(" 2. Update Car")
        println(" 3. List Cars")
        println(" 4. Search Cars")
        println(" 0. Return to main menu")
        println(" -1. Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun addCarData(carModel : CarModel) : Boolean {

        println()
        print("Enter car Number : ")
        carModel.carNo = readLine()!!.toInt()

        print("Enter Driver Name : ")
        carModel.driverName = readLine()!!

        print("Enter Navigator Name : ")
        carModel.navigatorName = readLine()!!

        return carModel.carNo.toUInt() != null && carModel.driverName.isNotEmpty() && carModel.navigatorName.isNotEmpty()
    }

    fun updateCarData(car : CarModel) : Boolean {

        val tempCarNo: Int?
        val tempDriverName: String?
        val tempNavigatorName: String?


        if (car != null) {
            print("Enter a new Car Number for [ " + car.carNo + " ] : ")
            tempCarNo = readLine()!!.toInt()
            print("Enter a new Description for [ " + car.driverName + " ] : ")
            tempDriverName = readLine()!!
            print("Enter a new Description for [ " + car.navigatorName + " ] : ")
            tempNavigatorName = readLine()!!

            if ( tempCarNo != null && !tempDriverName.isNullOrEmpty() && !tempNavigatorName.isNullOrEmpty()) {
                car.carNo = tempCarNo
                car.driverName = tempDriverName
                car.navigatorName = tempNavigatorName
                return true
            }
        }
        return false
    }

    fun listCars(cars : CarMemStore) {
        println("List All Cars")
        println()
        cars.logAll()
        println()
    }

    fun showCar(car : CarModel) {
        if(car != null)
            println("Car Details [ $car ]")
        else
            println("Car Not Found...")
    }

    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }

}