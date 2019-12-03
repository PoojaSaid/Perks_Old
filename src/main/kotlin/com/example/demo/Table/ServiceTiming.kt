package com.example.demo.Table

class ServiceTiming {
    companion object
    {
       const val STID  ="st_Id"
       const val STSrvid="st_srvId"
       const val STDayOfWeek="st_dayOfWeek"
       const val STTimeSlotNo="st_timeSlotNo"
       const val STStartTime="st_startTime"
       const val STEndTime="st_sndTime"
       const val STCreatedBy="st_createdBy"
       const val STCreatedDateTime="st_createdDateTime"
       const val STUpdatedBy ="st_updatedBy"
       const val STUpdatedDateTime ="st_updatedDateTime"
       const val STIsActive ="st_isActive"

        var deleteKeys = arrayListOf(STID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(STID)

        var primaryKeys = arrayListOf(STID)

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

