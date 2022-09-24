package org.patryk.rally.app.console.views

import org.patryk.rally.app.console.models.LocationMemStore
import org.patryk.rally.app.console.models.LocationModel

class LocationsView {

    fun locationMenu() : Int {

        var option : Int
        var input: String?

        println("Location MENU")
        println(" 1. Add Location")
        println(" 2. Update Location")
        println(" 3. List Locations")
        println(" 4. Search Locations")
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

    fun addLocationData(location : LocationModel) : Boolean {

        println()
        print("Enter Stage Number : ")
        location.stage = readLine()!!.toInt()

        println()
        print("Enter Corner Number : ")
        location.corner = readLine()!!.toInt()


        return location.stage.toUInt() != null && location.corner.toUInt() != null
    }

    fun updateLocationData(location: LocationModel) : Boolean {

        val tempStageNumber: Int?
        val tempCornerNumber: Int?

        if (location != null) {
            print("Enter a new stage number for [ " + location.stage + " ] : ")
            tempStageNumber = readLine()!!.toInt()
            print("Enter a new corner number for [ " + location.corner + " ] : ")
            tempCornerNumber = readLine()!!.toInt()


            if ( tempStageNumber != null && tempStageNumber != null) {
                location.stage = tempStageNumber
                location.corner = tempCornerNumber
                return true
            }
        }
        return false
    }

    fun listLocations(locations : LocationMemStore) {
        println("List All Location")
        println()
        locations.logAll()
        println()
    }

    fun showLocation(location : LocationModel) {
        if(location != null)
            println("Location Details [ $location ]")
        else
            println("Location Not Found...")
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