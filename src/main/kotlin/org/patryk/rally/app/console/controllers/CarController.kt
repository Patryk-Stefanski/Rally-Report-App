package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.CarModel
import java.sql.*
import java.util.*
import tornadofx.Controller


class CarController : Controller() {
    val db  = DataBaseController()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Car Menu" }
    }

    fun add(car: CarModel) : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (car.uid.isEmpty()) {
            car.uid = UUID.randomUUID().toString()
        } else throw IllegalArgumentException("UID should be set automatically")

        if (car.carNo.isEmpty())  {
            throw IllegalArgumentException("Car number is empty")
        }

        if (car.driverName.isEmpty() ) {
            throw IllegalArgumentException("Car driver name is empty")
        }

        if (car.navigatorName.isEmpty() ) {
            throw IllegalArgumentException("Car navigator name is empty")
        }


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Cars (uid,carNo,driverName,navigatorName) VALUES('${car.uid}','${car.carNo}','${car.driverName}','${car.navigatorName}') ")
        } catch (ex: SQLException) {
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

    fun list() : String  {
        var carList : String = ""
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
                    carList += "UID : " + rs.getString("uid") + "\n" + "CarNo : " + rs.getString("carNo") + "\n" + "Driver Name : " + rs.getString("driverName") + "\n"+ "Navigator Name : " + rs.getString("navigatorName") + "\n"+ "------------------------------------------" + "\n"
                }
            }
        } catch (ex: SQLException) {
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
        return carList
    }

    fun update(car: CarModel) : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        var updateQuery: String = "UPDATE Cars SET"


        if (car.carNo.isNotEmpty())  {
            updateQuery += " carNo = '${car.carNo}' "
            if (car.driverName.isNotEmpty() || car.navigatorName.isNotEmpty()){
                  updateQuery = "$updateQuery,"
            }
        }

        if (car.driverName.isNotEmpty()  ) {
            updateQuery += " driverName = '${car.driverName}' "
            if (car.navigatorName.isNotEmpty()){
                updateQuery = "$updateQuery,"
            }
        }

        if (car.navigatorName.isNotEmpty() ) {
            updateQuery += " navigatorName = '${car.navigatorName}' "
        }

        if (car.uid.isEmpty()) {
            throw IllegalArgumentException("Cannot update Car details because UID is empty")
        }  else updateQuery += " WHERE uid = '${car.uid}'"

        println(updateQuery)


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate(updateQuery)
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
        var query : String = ""

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

    fun delete(car: CarModel)  {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (car.uid.isEmpty()) {
            throw IllegalArgumentException("Cannot delete the car because UID is empty")
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("DELETE FROM `Cars` WHERE uid = '${car.uid}'")
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

}