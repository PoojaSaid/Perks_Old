package com.example.demo.Table

import com.example.demo.Extensions.SQLModel
import com.example.demo.Extensions.getDBConnection
import com.example.demo.Extensions.getResponseData
import com.example.demo.Extensions.jsonInsert
import com.example.demo.RESPONSE_TECH_ERROR
import org.json.JSONArray
import org.json.JSONObject
import java.sql.PreparedStatement
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList

//PENDING SOME WORK///
class AdminpanelCredentials {
    companion object {
        val TABLE_NAME = "AdminpanelCredentials"
        const val ID = "id"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val ROLEID = "role_id"
        const val APITOKEN = "api_token"
        var deleteKeys = arrayListOf(AdminpanelCredentials.ID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(AdminpanelCredentials.ID)

        var primaryKeys = arrayListOf(AdminpanelCredentials.ID)

    }

    fun getDeleteKeys(): ArrayList<String> {
        return deleteKeys
    }

    fun getPKKeys(): ArrayList<String> {
        return primaryKeys
    }


    fun shouldGeneratePK(): ArrayList<Boolean> {
        val al = ArrayList<Boolean>()
        al.add(generatePrimaryKey)
        return al
    }


    fun getAutoGeneratedColumnName(): ArrayList<String> {
        return autoGeneratedColumn
    }
}


class AdminPanelWS() {
    var lsMsg = ""
    fun adminLogin(email: String,
                   password: String
                   //roleID: Int
    ): String {
        var conn = getDBConnection()
        conn!!.autoCommit = false
        lateinit var preparedStatement: PreparedStatement
        var sqlStatements = ArrayList<SQLModel>()
        var roleID:Int=61

        //Validation
        val emailvalidation = Validation().maxRangeValidation(email)
        val passwordvalidation = Validation().passwordValidation(password)
        var validationDone: Boolean = true
        if (!passwordvalidation) {

            Validation().genMsg("Password should not be greater than 6 char")
            validationDone = false
        }
        if (!emailvalidation) {

            Validation().genMsg("Email should not be greater than 255 char")
            validationDone = false
        }


        //For Auto-generating Cust-ID
        val atomicInteger = AtomicInteger(3)
        var id = atomicInteger.getAndIncrement()
        var sub = "Authentication"
        var aud = "web/app"

        var isAPIToken = APIToken().apiToken("13324", "admin")

       /**val decodedToken = APIToken().decode(isAPIToken)
       println(decodedToken)**/

        var checkObject = JSONObject().apply {
            put(AdminpanelCredentials.EMAIL, email)
            put(AdminpanelCredentials.PASSWORD, password)
            put(AdminpanelCredentials.ID, id)
           put(AdminpanelCredentials.ROLEID,roleID)
            put(AdminpanelCredentials.APITOKEN, isAPIToken)
        }

        try {
            var jsonArray = JSONArray()
            jsonArray.put(checkObject)

            var insert = jsonInsert(jsonArray, AdminpanelCredentials.TABLE_NAME, conn!!)

            for (i in 0 until insert.size) {
                sqlStatements.add(insert[i])
            }

            try {
                for (i in 0 until sqlStatements.size) {
                    var e = sqlStatements[i]
                    var preparedStatement = conn!!.prepareStatement(e.statement)
                    preparedStatement.executeUpdate()

                }
                conn.commit()
            } catch (e: Exception) {
                conn!!.rollback()
                conn!!.close()
                var jsonResponses = getResponseData(RESPONSE_TECH_ERROR, e.toString())
            }
        } catch (e: Exception) {
            conn!!.rollback()
            return Validation().getStatusMessage("Failed", "Error while inserting record".plus("${e.message}")).toString()
        } finally {
            conn?.close()
        }
        return Validation().getStatusMessage("Success", "Record successfully Inserted")

    }

}
