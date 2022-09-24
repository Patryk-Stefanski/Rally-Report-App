package org.patryk.rally.app.console.views

class MainView {

    fun mainMenu() : Int {

        var option : Int
        var input: String?

        println("MAIN MENU")
        println(" 1. Login")
        println(" 2. Register User")
        println(" 3. Posts")
        println(" 4. Manage Locations (Admin)")
        println(" 5. Manage Cars (Admin)")
        println("-1. Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }


    fun Login(){
        var username = ""
        var password = ""

        println("Login")
        println("Enter username: ")
        if (readLine() != null && readLine()!!.isNotEmpty()) {
            username = readLine()!!
        } else {
            println("Error :  username is empty")
            return
        }

        println("Enter password: ")
        if (readLine() != null && readLine()!!.isNotEmpty()) {
            password = readLine()!!
        } else {
            println("Error :  password is empty")
            return
        }

    }


}