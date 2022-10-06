package org.patryk.rally.app.console.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import mu.KotlinLogging
import org.patryk.rally.app.console.models.Car
import java.sql.*
import java.util.*


class CarController {
    private val db = DataBaseController()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Car Menu" }
    }

    fun add(car: Car): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (car.uid.isEmpty()) {
            car.uid = UUID.randomUUID().toString()
        } else {
            logger.info { "UID should be set automatically" }
            return false
        }

        if (car.carNo.isEmpty()) {
            logger.info { "Car number is empty" }
            return false
        }

        if (car.driverName.isEmpty()) {
            logger.info { "Car driver name is empty" }
            return false
        }

        if (car.navigatorName.isEmpty()) {
            logger.info { "Car navigator name is empty" }
            return false
        }


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Cars (uid,carNo,driverName,navigatorName) VALUES('${car.uid}','${car.carNo}','${car.driverName}','${car.navigatorName}') ")
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
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
        return true
    }

    fun list(): ObservableList<String> {
        var carList: ObservableList<String> = FXCollections.observableArrayList()
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null

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
                    carList.add(
                        "UID : " + rs.getString("uid") + ", CarNo : " + rs.getString("carNo") + ", Driver Name : " + rs.getString(
                            "driverName"
                        ) + ", Navigator Name : " + rs.getString("navigatorName") + "\n"
                    )
                }
            }
        } catch (ex: SQLException) {
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
        return carList
    }

    fun update(car: Car): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        var updateQuery = "UPDATE Cars SET"


        if (car.carNo.isNotEmpty()) {
            updateQuery += " carNo = '${car.carNo}' "
            if (car.driverName.isNotEmpty() || car.navigatorName.isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (car.driverName.isNotEmpty()) {
            updateQuery += " driverName = '${car.driverName}' "
            if (car.navigatorName.isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (car.navigatorName.isNotEmpty()) {
            updateQuery += " navigatorName = '${car.navigatorName}' "
        }

        if (car.uid.isEmpty()) {
            logger.info { "Cannot update Car details because UID is empty" }
            return false
        } else updateQuery += " WHERE uid = '${car.uid}'"

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate(updateQuery)
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            return false
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
        return true
    }


    fun search(carUid: String): String {
        var car: String = ""

        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery("SELECT * FROM `Cars` where `uid`='${carUid}'")
            }
            if (rs != null) {
                while (rs.next()) {
                    car += "\n Car Details :" + "\n    Car Number : " + rs.getString("carNo") + "\n    Driver Name : " + rs.getString(
                        "driverName"
                    ) + "\n    Navigator Name : " + rs.getString("navigatorName")
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
        return car
    }

    fun delete(car: Car): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var foundUID = false

        if (car.uid.isEmpty()) {
            logger.info { "Cannot delete the car because UID is empty" }
            return false
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            rs = stmt!!.executeQuery("SELECT * FROM `Posts`")
            while (rs.next()) {
                if (rs.getString("uid") == car.uid) {
                    foundUID = true;
                }
            }
            if (!foundUID) {
                return false
            } else {
                stmt.executeUpdate("DELETE FROM `Cars` WHERE uid = '${car.uid}'")
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            return false
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
        return true
    }
}