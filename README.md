# Rally-Report-App

## Description 
Kotlin application , using mySQL for a persistant database and tornadoFX for UI.
Designed for rally fans to create posts about competitors at specific location on a stage.

## Features 
- Account regisration and authentication allows for multiple users.
- Two different types of users types admin and basic
- CRUD functionality on all models (described below)
- Ceratin Features locked to only admin accounts

## Models

### User
Contains username , password and admin account type (boolean)

### Car
Contains the car number , driver name and navigator name

### Location
Contains the stage number and corner number 


### Post
A social post that contains a title and date , and specifies which location and car the post relates to 
