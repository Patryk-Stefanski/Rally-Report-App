package org.patryk.rally.app.console.models

import java.util.UUID

interface UserStore {
    fun findAll(): List<UserModel>
    fun findOne(uid: UUID): UserModel?
    fun create(user : UserModel)
    fun update(user: UserModel)
}