package com.example.demo.Table

class ServiceTxnTechnician {
    companion object {
        val TABLE_NAME="servicetx_technician"
        const val STTID = "stt_id"
        const val STTStID = "stt_stid"
        const val STTTechslno = "stt_techSlno"
        const val STTTechID = "stt_techId"
        const val STTVisitDate = "stt_visitDate"
        const val STTStartTime = "stt_startTime"
        const val STTToTime = "stt_toTime"
        const val STTCreatedBy = "stt_createdBy"
        const val STTCreatedDateTime = "stt_createdDateTime"
        const val STTUpdatedBy = "stt_updatedBy"
        const val STTUpdatedDateTime = "stt_updatedDateTime"
        const val STTIsActive = "stt_isActive"

        var deleteKeys = arrayListOf(STTID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(STTID)

        var primaryKeys = arrayListOf(STTID)

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