package org.patryk.rally.app.console.models

import java.util.*
import javax.xml.stream.events.Comment

class PostModel(
    var id: Long = 0,
    var userModel: UserModel = UserModel(UUID.randomUUID() , username = "" , password = "" , admin = false),
    var carModel: CarModel = CarModel(id = 0 , carNo = 0 , driverName = "" , navigatorName = ""),
    var locationModel: LocationModel = LocationModel(id = 0 , stage = 0 , corner = 0),
    var title:String = "",
    var comment: Array<String> = arrayOf(String())
) {
}