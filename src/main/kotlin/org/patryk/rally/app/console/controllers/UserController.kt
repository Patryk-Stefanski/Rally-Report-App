package org.patryk.rally.app.console.controllers

import mu.KotlinLogging
import org.patryk.rally.app.console.models.User
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class UserController {
    private val logger = KotlinLogging.logger {}
    private val db = DataBaseController()

    init {
        logger.info { "Switching to User Management Menu" }
    }

    companion object {
        var thisUser: User = User("", "", 1)
    }

    fun login(user: User): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var validated = false

        if (user.username.isEmpty()) {
            throw IllegalArgumentException("Username field is empty")
        }

        if (user.password.isEmpty()) {
            throw IllegalArgumentException("Password field is empty")
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                stmt.executeQuery("SELECT * FROM Users where `username` = '${user.username}'")
                rs = stmt.resultSet
            }
            if (rs != null && rs.next()) {
                if (rs.getString("password") == user.password) {
                    validated = true

                    thisUser.username = user.username
                    thisUser.password = user.password
                    thisUser.admin = rs.getInt("admin")
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
        return validated
    }

    fun add(user: User): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (user.username.isEmpty()) {
            throw IllegalArgumentException("Username field is empty")
        }

        if (user.password.isEmpty()) {
            throw IllegalArgumentException("Password field is empty")
        }

        if (user.password == "NoMatch") {
            throw IllegalArgumentException("Passwords don't match")
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Users (username,password,admin) VALUES('${user.username}','${user.password}','${user.admin}')")
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
        return true
    }


    fun update(user: User): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (user.password.isEmpty()) {
            throw IllegalArgumentException("Password field is empty")
        }

        if (user.password == "NoMatch") {
            throw IllegalArgumentException("Passwords don't match")
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("UPDATE Users SET password = '${user.password}' where username = '${user.username}'")
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
        thisUser.password = user.password
        return true
    }

    fun delete(user: User): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (user.password.isEmpty()) {
            throw IllegalArgumentException("Password field is empty")
        }

        if (user.password == "NoMatch") {
            throw IllegalArgumentException("Passwords don't match")
        }

        if (user.password != thisUser.password) {
            println("pasword is incorect")
            return false
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("DELETE FROM `Users` WHERE username = '${user.username}'")
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
        return true
    }

}
