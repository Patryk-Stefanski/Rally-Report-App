package org.patryk.rally.app.console.models

import java.util.UUID

data class UserModel(var uid:UUID = UUID.randomUUID(), var username:String = "", var password:String = "", var admin:Boolean = false) {
}