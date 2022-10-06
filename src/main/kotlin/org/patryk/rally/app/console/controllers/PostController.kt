package org.patryk.rally.app.console.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import mu.KotlinLogging
import org.patryk.rally.app.console.models.*
import java.rmi.server.UID
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*


class PostController {
    private val db = DataBaseController()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info { "Switching to Post Menu" }
    }


    fun add(post: Post): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        if (post.uid.isEmpty()) {
            post.uid = UUID.randomUUID().toString()
        } else throw IllegalArgumentException("UID should be set automatically")

        if (post.userId.isEmpty()) {
            throw IllegalArgumentException("User isn't logged in")
        }

        if (post.carUid.isEmpty()) {
            logger.info { "No Car selected , failed to create new car" }
            return false
        }

        if (post.locationUid.isEmpty()) {
            logger.info { "No Location selected , failed to create new car" }
            return false
        }

        if (post.title.isEmpty()) {
            logger.info { "No Title set, failed to create new car" }
            return false
        }

        if (post.date.toString().isEmpty()) {
            logger.info { "No Date selected, failed to create new car" }
            return false
        }

        val carUid = post.carUid.substring(6, 42)
        val locationUid = post.locationUid.substring(6, 42)

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            stmt!!.executeUpdate("INSERT INTO Posts (uid,userUid,carUid,locationUid,title,comments,date) VALUES('${post.uid}','${post.userId}','${carUid}','${locationUid}','${post.title}','Comment','${post.date}') ")
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
        var postList: ObservableList<String> = FXCollections.observableArrayList()
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var carUid: String
        var locationUid: String

        var carController = CarController()
        var locationController = LocationController()


        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            if (stmt != null) {
                rs = stmt.executeQuery("SELECT * FROM `Posts`")
            }
            if (rs != null) {
                while (rs.next()) {
                    carUid = rs.getString("carUid")
                    locationUid = rs.getString("locationUid")
                    postList.add(
                        "UID : " + rs.getString("uid") + "\n Title : " + rs.getString("title") + "\n Date : " + rs.getString(
                            "date"
                        ) + "\n User : " + rs.getString("userUid") + carController.search(carUid) + locationController.search(
                            locationUid
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
        return postList
    }

    fun update(post: Post): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null

        var updateQuery = "UPDATE Posts SET"

        if (post.carUid.isNotEmpty()) {
            var carUID = post.carUid.substring(6, 42)
            updateQuery += " carUid = '${carUID}' "
            if (post.locationUid.isNotEmpty() || post.title.isNotEmpty() || post.date.toString().isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (post.locationUid.isNotEmpty()) {
            var locationUid = post.locationUid.substring(6, 42)
            updateQuery += " locationUid = '${locationUid}' "
            if (post.title.isNotEmpty() || post.date.toString().isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (post.title.isNotEmpty()) {
            updateQuery += " title = '${post.title}' "
            if (post.date.toString().isNotEmpty()) {
                updateQuery = "$updateQuery,"
            }
        }

        if (post.date.toString().isNotEmpty()) {
            updateQuery += " date = '${post.date}' "
        }

        if (post.uid.isEmpty()) {
            logger.info { "Cannot update Post details because UID is empty" }
            return false
        } else updateQuery += " WHERE uid = '${post.uid}'"

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

    fun delete(postUid: String): Boolean {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var foundUID = false

        if (postUid.isEmpty()) {
            logger.info { "Cannot delete the post because UID is empty" }
            return false
        }

        try {
            conn = db.getConnection()
            if (conn != null) {
                stmt = conn.createStatement()
            }
            rs = stmt!!.executeQuery("SELECT * FROM `Posts`")
            while (rs.next()) {
                if (rs.getString("uid") == postUid) {
                    foundUID = true;
                }
            }
            if (!foundUID) {
                return false
            } else {
                stmt.executeUpdate("DELETE FROM `Posts` WHERE uid = '${postUid}'")
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
        return true
    }
}
