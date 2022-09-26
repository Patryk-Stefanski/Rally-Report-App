package org.patryk.rally.app.console.models

import mu.KotlinLogging
import java.util.UUID

private val logger = KotlinLogging.logger {}


class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }


    override fun findOne(uid: UUID) : UserModel? {
        var foundUser: UserModel? = users.find { p -> p.uid == uid }
        return foundUser
    }

    override fun create(user: UserModel) {
        user.uid = UUID.randomUUID()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        var foundUser = findOne(user.uid)
        if ( foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
            foundUser.admin = user.admin
        }
    }

    internal fun logAll() {
        users.forEach { logger.info("UID : ${it.uid} , Username : ${it.username}")}
    }
}
