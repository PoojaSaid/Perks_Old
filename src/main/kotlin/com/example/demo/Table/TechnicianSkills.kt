package com.example.demo.Table

class TechnicianSkills {

    companion object
    {
        val TABLE_NAME="technician_skills"
       const val TSID ="ts_id"
       const val TechID="tech_id"
       const val TSServID="ts_servId"
       const val TSExpertise="ts_expertise"
       const val TSExperience="ts_experience"
       const val TSCreatedBy="ts_createdBy"
       const val TSCreatedDateTime="ts_createdDateTime"
       const val TSUpdatedBy="ts_updatedBy"
       const val TSUpdatedDatetime="ts_updatedDateTime"
       const val TSIsActive="ts_isActive"

        var deleteKeys = arrayListOf(TSID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(TSID)

        var primaryKeys = arrayListOf(TSID)

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