package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.UserMemStore
import org.patryk.rally.app.console.models.UserModel
import org.patryk.rally.app.console.views.UserManagementView
import java.util.*

class UserController {
    val users = UserMemStore()
    val logger = KotlinLogging.logger {}
    val userManagementView = UserManagementView()

    init {
        logger.info { "Switching to User Management Menu" }
    }

    fun start() {

        var input: Int

        do {
            input = userManagementView.userMenu()
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
        val user = UserModel()

        if (userManagementView.registerUser(user))
            users.create(user)
        else
            logger.info("User Not Registered")
    }

    fun list() {
        userManagementView.listUsers(users)
    }

    fun update() {

        userManagementView.listUsers(users)
        val searchId = userManagementView.getId()
        val user = search(searchId)

        if (user != null) {
            if (userManagementView.updateUserData(user)) {
                users.update(user)
                userManagementView.showUser(user)
                logger.info("User Updated : [ Username : ${user.username} , UID : ${user.uid} ]")
            } else
                logger.info("User Not Updated")
        } else
            println("User Not Updated...")
    }

    fun search() {
        val user = search(userManagementView.getId())!!
        userManagementView.showUser(user)
    }

    fun search(UID: UUID): UserModel? {
        val foundUser = users.findOne(UID)
        return foundUser
    }

    fun dummyData() {
        users.create(UserModel(uid = UUID.randomUUID(), username = "Root" , password = "root" , admin = true))
        users.create(UserModel(uid = UUID.randomUUID(), username = "test1" , password = "test1" , admin = false))
        users.create(UserModel(uid = UUID.randomUUID(), username = "test2" , password = "test2" , admin = false))
        users.create(UserModel(uid = UUID.randomUUID(), username = "test3" , password = "test3" , admin = false))

    }

    fun returnMainMenu() {
        MainController().start()
    }
}
