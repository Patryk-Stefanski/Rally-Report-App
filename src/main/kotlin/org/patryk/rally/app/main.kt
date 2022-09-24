package org.patryk.rally.app

import  mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    println("Rally Report App - v0.0.1")
    logger.info { "Launching Rally Report Console" }

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> println("Adding new Post")
            2 -> println("Updating Post")
            3 -> println("Listing all posts")
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