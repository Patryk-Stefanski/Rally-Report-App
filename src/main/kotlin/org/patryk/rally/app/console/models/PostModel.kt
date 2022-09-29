package org.patryk.rally.app.console.models

import java.util.*

class PostModel(
    var id: Long = 0,
    var userModel: UserModel = UserModel(UUID.randomUUID() , username = "" , password = "" , admin = false),
    var carModel: CarModel = CarModel(uid = "" , carNo = "" , driverName = "" , navigatorName = ""),
    var locationModel: LocationModel = LocationModel(uid = "" , stage = "" , corner = ""),
    var title:String = "",
    var comment: Array<String> = arrayOf(String())
) {
}