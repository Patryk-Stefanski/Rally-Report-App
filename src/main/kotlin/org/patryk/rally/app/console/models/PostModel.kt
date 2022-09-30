package org.patryk.rally.app.console.models

class PostModel(
    var id: Long = 0,
    var userModel: UserModel = UserModel("", "", 1),
    var carModel: CarModel = CarModel("", "", "", ""),
    var locationModel: LocationModel = LocationModel("", "", ""),
    var title: String = "",
    //to do :add date var date:Date =
    var comment: Array<String> = arrayOf(String()),
) {
}