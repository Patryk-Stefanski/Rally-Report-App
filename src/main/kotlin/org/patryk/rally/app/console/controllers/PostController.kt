package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.*
import org.patryk.rally.app.console.views.CarView
import org.patryk.rally.app.console.views.LocationsView
import org.patryk.rally.app.console.views.PostView

class PostController {
    val posts = PostMemStore()
    val postView = PostView()
    val logger = KotlinLogging.logger {}

    fun start() {

        var input: Int

        do {
            input = postView.PostMenu()
            when (input) {
                1 -> add()
                2 -> update()
                3 -> list()
                4 -> search()
                0 -> returnMainMenu()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Rally Report Console App" }
    }

    fun add() {
        val post = PostModel()

        if (postView.addPostData(post))
            posts.create(post)
        else
            logger.info("Post Not Added")
    }

    fun list() {
        postView.listPosts(posts)
    }

    fun update() {

        postView.listPosts(posts)
        val searchId = postView.getId()
        val post = search(searchId)

        if (post != null) {
            if (postView.updatePostData(post)) {
                posts.update(post)
                postView.showPost(post)
                logger.info("Post Updated : [ $post ]")
            } else
                logger.info("Post Not Updated")
        } else
            println("Post Not Updated...")
    }

    fun search() {
        val post = search(postView.getId())!!
        postView.showPost(post)
    }

    fun search(id: Long): PostModel? {
        val foundPost = posts.findOne(id)
        return foundPost
    }


    fun returnMainMenu() {
        MainController().start()
    }

}