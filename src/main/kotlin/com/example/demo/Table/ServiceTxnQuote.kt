package com.example.demo.Table

class ServiceTxnQuote {
    companion object
    {
        val TABLE_NAME="servicetxn_quote"
        const val STQID="stq_id"
        const val STQStID="stq_stid"
        const val STQQuotationNo="stq_quotationNo"
        const val STQMaterialCost="stq_materialCost"
        const val STQLaborCost="stq_laborCost"
        const val STQTaxes="stq_taxes"
        const val STQQuotationDetails="stq_quotationDetails"
        const val STQTotal="stq_total"
        const val STQStatus="stq_status"
        const val STQRejreason="stq_rejreason"
        const val STQCreatedBy="stq_createdBy"
        const val STQCreatedDateTime="stq_createdDateTime"
        const val STQUpdatedBy="stq_updatedBy"
        const val STQUpdatedDateTime="stq_updatedDateTime"
        const val STQIsActive="stq_isActive"

        var deleteKeys = arrayListOf(STQID)
        var generatePrimaryKey = true

        var autoGeneratedColumn = arrayListOf(STQID)

        var primaryKeys = arrayListOf(STQID)

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