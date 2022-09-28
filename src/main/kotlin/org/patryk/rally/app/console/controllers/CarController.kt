package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.CarModel
import org.patryk.rally.app.console.views.CarView
import java.sql.*
import java.util.*


class CarController {
    val db  = DataBaseController()
    val carView = CarView()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Car Menu" }
    }

    fun start() {

        var input: Int

        do {
            input = carView.carMenu()
            when (input) {
                1 -> add()
                2 -> update()
                3 -> list()
                4 -> search()
                5 -> delete()
                0 -> returnMainMenu()
                //-99 -> dummyData()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Rally Report Console App" }
    }

    fun add() : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        val car = CarModel()


        if (!carView.addCarData(car)){
            false
        }

        car.uid = UUID.randomUUID().toString()

        //Add error checking

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Cars (uid,carNo,driverName,navigatorName) VALUES('${car.uid}','${car.carNo}','${car.driverName}','${car.navigatorName}') ")
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
        return true
    }

    fun list() {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs : ResultSet? = null

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery("SELECT * FROM `Cars`")
            }
            if (rs != null) {
                while (rs.next()) {
                    println("UID : " + rs.getString("uid"))
                    println("CarNo : " + rs.getInt("carNo"))
                    println("Driver Name : " + rs.getString("driverName"))
                    println("Navigator Name : " + rs.getString("navigatorName"))
                    println("-----------------------------------------------")
                }
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
    }

    fun update() : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var car = CarModel()

        list()

        if (!carView.updateCarData(car)){
            return false
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("UPDATE Cars SET  driverName = '${car.driverName}', navigatorName = '${car.navigatorName}', carNo = '${car.carNo}'  WHERE uid = '${car.uid}'")
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
        return false
    }


    fun search() {
        var carView = CarView()
        var query : String = ""
        query = carView.searchBy()

        //Update for use with SQL

        var conn: Connection? = null
        var stmt: Statement? = null
        var rs : ResultSet? = null

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery(query)
            }
            if (rs != null) {
                while (rs.next()) {
                    println("UID : " + rs.getString("uid"))
                    println("CarNo : " + rs.getInt("carNo"))
                    println("Driver Name : " + rs.getString("driverName"))
                    println("Navigator Name : " + rs.getString("navigatorName"))
                    println("-----------------------------------------------")
                }
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
    }

    fun delete()  {
        var conn: Connection? = null
        var stmt: Statement? = null

        var car = CarModel()

        list()

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (carView.deleteCarData(car)) {
                stmt!!.executeUpdate("DELETE FROM `Cars` WHERE uid = '${car.uid}'")
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
    }

    fun returnMainMenu() {
        MainController().start()
    }

}