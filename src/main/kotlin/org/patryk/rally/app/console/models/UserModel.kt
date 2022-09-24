package org.patryk.rally.app.console.models

import java.util.UUID

class UserModel(var uid:UUID, var username:String, var password:String, var admin:Boolean) {
}