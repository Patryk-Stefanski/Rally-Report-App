package org.patryk.rally.app.console.models

import java.util.*

interface PostStore {
    fun findAll(): List<PostModel>
    fun findOne(id: Long): PostModel?
    fun create(post : PostModel)
    fun update(post: PostModel)
}