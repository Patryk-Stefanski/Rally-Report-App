package org.patryk.rally.app

import  mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    println("Rally Report App - v0.0.2")
    logger.info { "Launching Rally Report Console" }

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> addPost()
            2 -> updatePost()
            3 -> viewPosts()
            4 -> deletePost()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Rally Report Console App" }
}

fun menu() : Int {

    var option : Int
    var input: String? = null

    println("Main Menu")
    println(" 1. Add Post")
    println(" 2. Update Post")
    println(" 3. View Posts")
    println(" 4. Delete Post")
    println("-1. Exit")
    println()
    print("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}

fun addPost(){
    println("You Chose Add Post")
}

fun updatePost() {
    println("You Chose Update Post")
}

fun viewPosts() {
    println("You Chose View Posts")
}

fun deletePost() {
    println("You Chose Delete Post")
}