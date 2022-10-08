package org.patryk.rally.app.console.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import mu.KotlinLogging
import org.patryk.rally.app.console.models.Location
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class LocationController {
    val db = DataBaseController()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Location Menu" }
    }

    fun add(location: Location): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (location.uid.isEmpty()) {
            location.uid = UUID.randomUUID().toString()
        } else {
            logger.info { "UID should be set automatically" }
            return false
        }

        if (location.stage.isEmpty()) {
            logger.info { "Location stage number is empty" }
            return false
        }

        if (location.corner.isEmpty()) {
            logger.info { "Location corner number is empty" }
            return false
        }


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Locations (uid,stage,corner) VALUES('${location.uid}','${location.stage}','${location.corner}') ")
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
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

    fun list(): ObservableList<String> {
        var locationList: ObservableList<String> = FXCollections.observableArrayList()
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null

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
                    locationList.add(
                        "UID : " + rs.getString("uid") + ", Stage Number : " + rs.getString("stage") + ", Corner Number : " + rs.getString(
                            "corner"
                        ) + "\n"
                    )
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

    fun update(location: Location): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet?
        var foundUID = false

        var updateQuery = "UPDATE `Locations` SET"


        if (location.stage.isNotEmpty()) {
            updateQuery += " stage = '${location.stage}' "
            if (location.corner.isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (location.corner.isNotEmpty()) {
            updateQuery += " corner = '${location.corner}' "
        }

        if (location.corner.isEmpty() && location.stage.isEmpty()){
            logger.info { "All fields are empty" }
            return  false
        }


        if (location.uid.isEmpty()) {
            logger.info { "Cannot update Location details because UID is empty" }
            return false
        } else updateQuery += " WHERE uid = '${location.uid}'"




        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            rs = stmt!!.executeQuery("SELECT * FROM `Locations`")
            while (rs.next()) {
                if (rs.getString("uid") == location.uid) {
                    foundUID = true;
                }
            }
            if (!foundUID) {
                return false
            } else {
                stmt.executeUpdate(updateQuery)
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            return false
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

    fun delete(location: Location): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var foundUID = false

        if (location.uid.isEmpty()) {
            logger.info { "Cannot delete the location because UID is empty" }
            return false
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            rs = stmt!!.executeQuery("SELECT * FROM `Locations`")
            while (rs.next()) {
                if (rs.getString("uid") == location.uid) {
                    foundUID = true;
                }
            }
            if (!foundUID) {
                return false
            } else {
                stmt.executeUpdate("DELETE FROM `Locations` WHERE uid = '${location.uid}'")
            }

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            return false
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

    fun search(locationUid: String): String {
        var location: String = ""

        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery("SELECT * FROM `Locations` where `uid`='${locationUid}'")
            }
            if (rs != null) {
                while (rs.next()) {
                    location += "\n Location Details :" + "\n    Stage Number : " + rs.getString("stage") + "\n    Corner Number : " + rs.getString(
                        "corner"
                    )
                }
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (_: SQLException) {
                }
            }
            if (conn != null) {
                try {
                    conn.close()
                } catch (_: SQLException) {
                }
            }
        }
        return location
    }

}