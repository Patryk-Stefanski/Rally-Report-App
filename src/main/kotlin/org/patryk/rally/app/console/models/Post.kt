package org.patryk.rally.app.console.models

import java.time.LocalDate

class Post(
    var uid: String,
    var userId: String,
    var carUid: String,
    var locationUid: String,
    var title: String,
    var date: LocalDate,
    var comments: String,
)