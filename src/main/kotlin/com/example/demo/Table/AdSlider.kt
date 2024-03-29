package com.example.demo.Table

import com.example.demo.Extensions.*
import com.example.demo.RESPONSE_TECH_ERROR
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.web.multipart.MultipartFile
import java.sql.PreparedStatement
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList

class AdSlider {
    companion object {
        val TABLE_NAME = "AdSlider"
        const val id = "id"
        const val title = "title"
        const val description = "description"
        const val fileName = "file_name"
        const val active = "active"
        var deleteKeys = arrayListOf(id)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(id)

        var primaryKeys = arrayListOf(id)

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


class AdSliderWS() {

    fun getAllAdSlider(
    ): String {

        var conn = getDBConnection()

        var IsBaseSQL = " FROM ${AdSlider.TABLE_NAME}"

        var IsSQL = "SELECT *"

        var IsWhere = " WHERE ${AdSlider.active}='Y'"

        var IsFinalSQL = IsSQL + IsBaseSQL + IsWhere

        var jsonArray = rowsToJsonArray(IsFinalSQL, conn)

        var stmt = conn!!.createStatement()
        var rs = stmt.executeQuery(IsFinalSQL)

        var JSONRESULT = JSONObject().apply {
            put("DATA", jsonArray)
            put("STATUS", DBFunctions.SUCCESS)
            put("SQL", IsFinalSQL)
        }
        conn!!.close()

        return JSONRESULT.toString()
    }

    fun createAdSlider(
            title: String,
            description: String,
            image: MultipartFile?
    ): String {
        var active = 'Y'
        var conn = getDBConnection()
        conn!!.autoCommit = false

        lateinit var prepareStatement: PreparedStatement
        var sqlStatement = ArrayList<SQLModel>()

        //Validation
        var msg = "Validation Error"
        var titleValidation = Validation().titleValidation(title)
        //var imageValidation=Validation().imageValidation(image)

        var validationDone: Boolean = true
        if (!titleValidation) {
            Validation().genMsg("Title should not greater than 100")
            validationDone = false
        }

//        if (!imageValidation)
//        {
//            Validation().genMsg("Image should not greater than 100")
//          validationDone=false
//        }

        //Primary key Generation
        val atomiInteger = AtomicInteger()
        val id = atomiInteger.getAndIncrement()

        //Image file path
        var fileName = ""
        if (image != null) {
            if (image.isEmpty.not()) {
                var filePath = Validation().getPath()
                val uuid = UUID.randomUUID()
                fileName = "storage/images/sliders" + uuid + ".jpeg"

            } else {
                return fileName
            }
        }

        var checkObject = JSONObject().apply {
            put(AdSlider.active, active)
            put(AdSlider.id, id)
            put(AdSlider.title, title)
            put(AdSlider.description, description)
            put(AdSlider.fileName, fileName)
        }

        try {
            var jsonArray = JSONArray()
            jsonArray.put(checkObject)
            var insert = jsonInsert(jsonArray, AdSlider.TABLE_NAME, conn!!)

            for (i in 0 until insert.size) {
                sqlStatement.add(insert[i])
            }

            try {
                for (i in 0 until sqlStatement.size) {
                    var e = sqlStatement[i]
                    var prepareStatement = conn!!.prepareStatement(e.statement)
                    prepareStatement.executeUpdate()
                }
                conn?.commit()
            } catch (e: Exception) {
                conn!!.rollback()
                conn!!.close()
                var jsonResponse = getResponseData(RESPONSE_TECH_ERROR, e.toString())

            }
        } catch (e: Exception) {
            conn?.rollback()
            return Validation().getStatusMessage("Failed", "Error while inserting record".plus("${e.message}")).toString()
        } finally {
            conn?.close()
        }
        return Validation().getStatusMessage("Success", "Record successfully Inserted")
    }


    fun editAdSlider(
            id: Int,
            title: String,
            description: String,
            image: MultipartFile?

    ): String {

        var conn = getDBConnection()
        conn!!.autoCommit = false

        var sqlStatement = ArrayList<SQLModel>()
        var IsSQL = ""
        var active = 'Y'

        //Image file path
        var fileName = ""
        if (image != null) {
            if (image.isEmpty.not()) {
                var filePath = Validation().getPath()
                val uuid = UUID.randomUUID()
                fileName = "storage/images/sliders" + uuid + ".jpeg"

            } else {
                return fileName
            }
        }

        var checkObject = JSONObject().apply {
            put(AdSlider.active, active)
            put(AdSlider.id, id)
            put(AdSlider.title, title)
            put(AdSlider.description, description)
            put(AdSlider.fileName, fileName)
        }


        if (id != null) {
            IsSQL = " DELETE FROM ${AdSlider.TABLE_NAME}" +
                    " WHERE" +
                    " ${AdSlider.id}='$id'"

            execUpdate(conn!!, IsSQL)
        } else {
            val atomiInteger = AtomicInteger(11)
            val id = atomiInteger.getAndIncrement()
            checkObject.put(AdSlider.id, id)
        }

        try{
            var jsonArray=JSONArray()
            jsonArray.put(checkObject)

            var insert= jsonInsert(jsonArray,AdSlider.TABLE_NAME,conn!!)

            for (i in 0 until insert.size)
            {
                sqlStatement.add(insert[i])
            }

            try{
                for (i in 0 until sqlStatement.size)
                {
                    var e= sqlStatement[i]
                    var preparedStatement=conn!!.prepareStatement(e.statement)
                    preparedStatement.executeUpdate()
                }
                conn!!.commit()
            }
            catch (e:Exception){
                conn!!.rollback()
                conn!!.close()
                var jsonResponse= getResponseData(RESPONSE_TECH_ERROR.toString(),e.toString())
             }
        }
        catch (e:java.lang.Exception){
            conn?.rollback()
            return Validation().getStatusMessage("Failed", "Error while inserting record".plus("${e.message}")).toString()
        }
        finally {
            conn?.close()
        }

        return Validation().getStatusMessage("Success", "Record successfully Inserted")
    }

    fun deleteAdSlider(
            id:Int
    ):String {
        var conn= getDBConnection()
        var IsSQL="SELECT count(1) dataCount " +
                " FROM ${AdSlider.TABLE_NAME} " +
                " WHERE ${AdSlider.id}='$id'"

        var stmt=conn!!.createStatement()
        var rs=stmt.executeQuery(IsSQL)

        var totRows=0

        while (rs!!.next())
        {
            totRows=rs.getNullableInt("dataCount")!!
        }

        IsSQL=" DELETE FROM ${AdSlider.TABLE_NAME} "+
                "WHERE" +
                " ${AdSlider.id}='$id'"
        execUpdate(conn!!,IsSQL)
        return Validation().getStatusMessage("Success", "Record successfully Inserted")
    }
}
