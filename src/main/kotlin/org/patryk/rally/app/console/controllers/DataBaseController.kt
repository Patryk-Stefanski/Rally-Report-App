package org.patryk.rally.app.console.controllers

import java.sql.*


class DataBaseController {

    private var con: Connection? = null

    fun getConnection(): Connection? {
        try {
            con = DriverManager.getConnection("jdbc:" + "mysql" + "://" +
            "localhost" +
                    ":" + "3306" + "/" +
                    "RallyAppDB" , "root" , "" );
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
        return con
    }
}


