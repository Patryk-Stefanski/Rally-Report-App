package org.patryk.rally.app.console.views

import org.patryk.rally.app.console.models.*


class PostView {
    val carView = CarView()
    val cars = CarMemStore()
    val locationsView = LocationsView()
    val locations = LocationMemStore()


    fun PostMenu() : Int {

        var option : Int
        var input: String?

        println("POST MENU")
        println(" 1. Add Post")
        println(" 2. Update Post")
        println(" 3. List Posts")
        println(" 4. Search Posts")
        println(" 0. Return to main menu")
        println(" -1. Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun addPostData(post : PostModel) : Boolean {

        //Add post.user = currentUser (loggedIN)

        println()

        //List all cars
        carView.listCars(cars)


        print("Enter car ID : ")

        post.carModel = cars.findOne(readLine()!!.toLong())!!

        //List location
        locationsView.listLocations(locations)

        print("Enter location id :")
        post.locationModel = locations.findOne(readLine()!!.toLong())!!

        print("Enter post title : ")
        post.title = readLine().toString()

        return post.carModel != null && post.locationModel != null && post.title.isNotEmpty()
    }

    fun updatePostData(post : PostModel) : Boolean {

        //Add check if post.user == currentUser

        val tempCar: CarModel?
        val tempLocation: LocationModel?
        val tempTitle : String




        if (post != null) {
            carView.listCars(cars)
            print("Enter id of the new Car inplace of  [ " + post.carModel + " ] :" )
            tempCar = cars.findOne(readLine()!!.toLong())

            locationsView.listLocations(locations)
            print("Enter the id of the new Location inplace of  [ " + post.locationModel + " ] :" )
            tempLocation = locations.findOne(readLine()!!.toLong())

            print("Enter a new title for [" + post.title + "] : ")
            tempTitle = readLine().toString()




            if ( tempCar != null && tempLocation != null && tempTitle.isNotEmpty()) {
                post.carModel = tempCar
                post.locationModel = tempLocation
                post.title = tempTitle
                return true
            }
        }
        return false
    }

    fun listPosts(posts : PostMemStore) {
        println("List All Posts")
        println()
        posts.logAll()
        println()
    }

    fun showPost(post : PostModel) {
        if(post != null)
            println("Post Details [ $post ]")
        else
            println("Post Not Found...")
    }

    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }


}