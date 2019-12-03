package com.example.demo.Table

import com.example.demo.Extensions.DBFunctions
import com.example.demo.Extensions.getDBConnection
import com.example.demo.Extensions.getNullableInt
import com.example.demo.Extensions.rowsToJsonArray
import org.json.JSONObject
import org.springframework.web.bind.annotation.RequestParam

class Technician
{
 companion object
 {
     val TABLE_NAME="technician"
     const val TechID="tech_Id"
     const val TechFirstName="tech_firstName"
     const val TechMiddleName="tech_middleName"
     const val TechLastName="tech_lastName"
     const val TechMobileNo="tech_mobileNo"
     const val TechAltMibileNo="tech_altMobileNo"
     const val TechEmail="tech_email"
     const val TechImage="tech_image"
     const val TechCreatedBy="tech_createdBy"
     const val TechCreatedDateTime="tech_createdDateTime"
     const val TechUpdatedBy="tech_updatedBy"
     const val TechUpdatdDateTime="tech_updatedDateTime"
     const val TechIsActive="tech_isActive"
 }
}

class TechnicianWS
{
    fun getTechnician(
            @RequestParam ("techID",required=true)techID: String
    ):String{
        var conn= getDBConnection()
        var jsonResult=JSONObject()

        var IsBaseTableSql="FROM technician"

        var IsSql=" SELECT * "

        var IsWhere=""

        if (techID.isNullOrEmpty().not())
        {
            IsWhere=" WHERE ${Technician.TechID}='$techID'"
        }

        IsSql =IsSql+IsBaseTableSql+IsWhere

        var jsonArray= rowsToJsonArray(IsSql,conn)

        var IsPageSql="SELECT COUNT(*) as dataCount " + IsBaseTableSql + "$IsWhere"

        var stmt=conn!!.createStatement()
        var rs=stmt.executeQuery(IsPageSql)

        var totalRow=0

        while(rs!!.next())
        {
            totalRow=rs.getNullableInt("dataCount")!!
        }

        jsonResult.put("TOTAL_ROWS", totalRow)
        jsonResult.put("DATA", jsonArray)
        jsonResult.put("STATUS", DBFunctions.SUCCESS)
        jsonResult.put("SQL", IsSql)

         conn!!.close()
        return jsonResult.toString()
    }

//    fun postTehnician(
//            id:Int,
//            firstName:String,
//            lastName:String,
//            mobileNo:String,
//
//
//    )
}