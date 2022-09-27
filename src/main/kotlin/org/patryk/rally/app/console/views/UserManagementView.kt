package org.patryk.rally.app.console.views

import org.patryk.rally.app.console.models.*
import java.util.UUID

class UserManagementView {

    fun userMenu() : Int {

        var option : Int
        var input: String?

        println("USER MANAGEMENT MENU")
        println(" 1. Register User")
        println(" 2. Update User")
        println(" 3. List User")
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

    fun registerUser(user : UserModel) : Boolean {

        println("Register")
        println("Enter username: ")
        user.username = readLine()!!

        println("Enter password: ")
        user.password = readLine()!!

        user.admin = false

        return user.username.isNotEmpty() && user.password.isNotEmpty()
    }

    fun updateUserData(user : UserModel) : Boolean {

        var tempUserName: String?
        var tempPassword: String?

        if (user != null) {

            print("Enter password :")
            if (readLine() != user.password) {
                println("Error : Incorrect Password")
                return  false
            }

            print("Enter a new Username  for [ " + user.username + " ] : ")
            tempUserName = readLine()!!
            print("Enter a new password or leave empty to keep old password :")
            tempPassword = readLine()!!
            if (tempPassword == "") {
                tempPassword = user.password
            }

            if (!tempUserName.isNullOrEmpty() && !tempPassword.isNullOrEmpty()) {
                user.username = tempUserName
                user.password = tempPassword
                return true
            }
        }
        return false
    }

    fun showUser(user : UserModel) {
        if(user != null)
            println("User Details [ ${user.uid} , ${user.username} ]")
        else
            println("User Not Found...")
    }

    fun listUsers(users : UserMemStore) {
        println("List All Users")
        println()
        users.logAll()
        println()
    }

    fun getId() : UUID {
        var strId : String? // String to hold user input
        var searchId : UUID // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
         if (!strId.isEmpty()) {
            searchId = UUID.fromString(strId)
             return  searchId
        } else return UUID.fromString("")
    }


}