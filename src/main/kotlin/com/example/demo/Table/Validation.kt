package com.example.demo.Table

import com.example.demo.Response_Validate_Error
import org.json.JSONObject
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.regex.Pattern

class Validation {
 var lsMsg=""
    fun maxRangeValidation(msg: String): Boolean {

        if (msg.length > 255) {

            return false
        }
        return true
    }


    fun mobileNoValidation(msg: String): Boolean {
        if ( msg.length<10 || msg.length >10 ) {
            return false
        }
        return true
    }


    fun passwordValidation(msg: String): Boolean {
        if (msg.length < 6) {
           return  false
        }
        return true
    }

    fun titleValidation(msg: String): Boolean {
        if (msg.length >100) {
           return  false
        }
        return true
    }

    fun imageValidation(msg: String): Boolean {
        if (msg.length>100) {
           return  false
        }
        return true
    }

    fun validationMsg(msg: String) =
            JSONObject().apply {
                put("Status", "failed")
                put("Message", msg)
                put("Error", Response_Validate_Error)
            }

    fun genMsg(msg: String) {

        lsMsg = lsMsg + msg
    }

    fun getStatusMessage(Std: String, Msg: String): String {
        var jsonResponse = JSONObject()
        jsonResponse.put("Status", Std)
        jsonResponse.put("MESSAGE",Msg )
        return jsonResponse.toString()
    }

    fun getPath(): String {
        var tomcatBase = System.getProperty("catalina.base");
        var pathString = "/webapps/storage/img";
        var filePath = tomcatBase + pathString

        if (!(File(filePath).exists())) {
            File(filePath).mkdirs();


            var lsLibFileForPermission = File(filePath)
            lsLibFileForPermission.setReadable(true, false)
            lsLibFileForPermission.setExecutable(true, false)
            lsLibFileForPermission.setWritable(true, false)
        }
        return filePath;
    }

}