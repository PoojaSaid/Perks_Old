package com.example.demo.Table

class AdminpanelDevices {
    companion object {
        const val ID = "id"
        const val DEVICEID = "deviceId"
        const val CREATEDAT = "createdAt"
        const val UPDATEDAT = "updatedAt"
        const val DEVICESTATUS = "deviceStatus"

        var deleteKeys = arrayListOf(ID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(ID)

        var primaryKeys = arrayListOf(ID)

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