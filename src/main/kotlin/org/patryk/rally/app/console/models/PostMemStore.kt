package org.patryk.rally.app.console.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastPostId = 0L

internal fun getPostId(): Long {
    return lastPostId++
}

class PostMemStore : PostStore {

    val posts = ArrayList<PostModel>()

    override fun findAll(): List<PostModel> {
        return posts
    }

    override fun findOne(id: Long) : PostModel? {
        var foundPost: PostModel? = posts.find { p -> p.id == id }
        return foundPost
    }

    override fun create(post: PostModel) {
        post.id = getPostId()
        posts.add(post)
        logAll()
    }

    override fun update(post: PostModel) {
        var foundPost = findOne(post.id!!)
        if (foundPost != null) {
            foundPost.carModel = post.carModel
            foundPost.locationModel = post.locationModel
            foundPost.title = post.title
            foundPost.comment = post.comment
        }
    }

    internal fun logAll() {
        posts.forEach { logger.info("$it") }
    }
}
