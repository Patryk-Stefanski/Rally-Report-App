package org.patryk.rally.app.console.views

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
        println(" 5. Delete Car")
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

    fun addCarData(car : CarModel) : Boolean {

        println()
        print("Enter car Number : ")
        car.carNo = readLine()!!.toInt()

        println(car.carNo)

        print("Enter Driver Name : ")
        car.driverName = readLine()!!

        print("Enter Navigator Name : ")
        car.navigatorName = readLine()!!

        return car.carNo.toUInt() != null && car.driverName.isNotEmpty() && car.navigatorName.isNotEmpty()
    }

    fun updateCarData(car : CarModel) : Boolean {

        val tempCarNo: Int?
        val tempDriverName: String?
        val tempNavigatorName: String?
        val tempUID : String?

        println("Please enter the UID of the car you wish to update : ")
        tempUID = readLine()!!

        if (car != null) {
            print("Enter a new Car Number for [ " + car.carNo + " ] : ")
            tempCarNo = readLine()!!.toInt()
            print("Enter a new Driver Name for [ " + car.driverName + " ] : ")
            tempDriverName = readLine()!!
            print("Enter a new Navigator Name for [ " + car.navigatorName + " ] : ")
            tempNavigatorName = readLine()!!

            if ( tempCarNo != null && !tempDriverName.isNullOrEmpty() && !tempNavigatorName.isNullOrEmpty()) {
                car.carNo = tempCarNo
                car.driverName = tempDriverName
                car.navigatorName = tempNavigatorName
                car.uid = tempUID
                return true
            }
        }
        return false
    }

    fun deleteCarData(car : CarModel) : Boolean {

        println()
        print("Enter the UID of the car you wish to delete : ")
        car.uid = readLine()!!


        return car.uid.isNotEmpty()
    }


    fun searchBy(): String {
        var criteriaChoice: Int = 0

        println("Choose which criteria to search by : ")
        println(" 1 - Car number")
        println(" 2 - Driver Name")
        println(" 3 - Navigator Name")

        criteriaChoice = readLine()!!.toInt()


        println("Please enter what you would like to search for")

        if ( criteriaChoice == 1) {
            return ("SELECT * FROM Cars WHERE carNo LIKE '${readLine()!!}'")
        }
        if ( criteriaChoice == 2) {
            return ("SELECT * FROM Cars WHERE driverName LIKE '${readLine()!!}'")
        }
        if ( criteriaChoice == 3) {
            return ("SELECT * FROM Cars WHERE navigatorName LIKE '${readLine()!!}'")
        }

        return ""
    }


    fun listCars(cars : String) {
        println("List All Cars")
        println(cars)
        println()
    }

    fun showCar( car : String) {
        if(car.isNotEmpty())
            println("Car Details [ $car ]")
        else
            println("Car Not Found...")
    }

    fun getId() : String {
        var strId : String? // String to hold user input
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        return strId
    }

}