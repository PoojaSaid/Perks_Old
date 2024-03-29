package com.example.demo.Table

class Access_Rights {
    companion object {

        const val ID="id"
        const val rOLEID="role_id"
        const val ACCESS_FUNCTION_ID="access_function_id"
        const val PARENT_CATEGORY_ID="parent_category_id"
        const val RIGHTS="rights"
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
