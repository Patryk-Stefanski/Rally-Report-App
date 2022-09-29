package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.LocationModel
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class LocationController {
    val db  = DataBaseController()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Location Menu" }
    }

    fun add(location: LocationModel) : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (location.uid.isEmpty()) {
            location.uid = UUID.randomUUID().toString()
        } else throw IllegalArgumentException("UID should be set automatically")

        if (location.stage.isEmpty())  {
            throw IllegalArgumentException("Location stage number is empty")
        }

        if (location.corner.isEmpty() ) {
            throw IllegalArgumentException("Location corner number is empty")
        }


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Locations (uid,stage,corner) VALUES('${location.uid}','${location.stage}','${location.corner}') ")
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
        var locationList : String = ""
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs : ResultSet? = null

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery("SELECT * FROM `Locations`")
            }
            if (rs != null) {
                while (rs.next()) {
                    locationList += "UID : " + rs.getString("uid") + "\n" + "Stage Number : " + rs.getString("stage") + "\n" + "Corner Number : " + rs.getString("corner") + "\n"+  "------------------------------------------" + "\n"
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
        return locationList
    }

    fun update(location: LocationModel) : Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        var updateQuery: String = "UPDATE Locations SET"


        if (location.stage.isNotEmpty())  {
            updateQuery += " stage = '${location.stage}' "
            if (location.corner.isNotEmpty()){
                updateQuery = "$updateQuery,"
            }
        }

        if (location.corner.isNotEmpty()  ) {
            updateQuery += " corner = '${location.corner}' "
        }


        if (location.uid.isEmpty()) {
            throw IllegalArgumentException("Cannot update Location details because UID is empty")
        }  else updateQuery += " WHERE uid = '${location.uid}'"

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

    fun delete(location: LocationModel)  {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (location.uid.isEmpty()) {
            throw IllegalArgumentException("Cannot delete the location because UID is empty")
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("DELETE FROM `Locations` WHERE uid = '${location.uid}'")
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