package com.example.demo.Table

import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class APIToken {
    val SECRET_KEY = "FREE_MASON" //@TODO Add Signatu
    val HEX_ARRAY = "0123456789ABCDEF".toCharArray()
    val ISSUER = "https://api.perksapp.in/api/loginadmin"
    val JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}"
    var payload: JSONObject = JSONObject()
    var signature: String? = null
    var encodedHeader: String? = null
    fun JWebToken(): String {
        encodedHeader = encode(JSONObject(JWT_HEADER))
        return encodedHeader.toString()
    }

    fun apiToken(sub: String, aud: String): String {
        var payload = JSONObject().apply {
            put("iss", ISSUER)
            put("sub", sub)
            put("aud", aud)
            put("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        }
        return encode(payload)
    }


    @Throws(NoSuchAlgorithmException::class)
    fun JWebToken(token: String) {
        val parts = token.split("\\.").toTypedArray()
        require(parts.size == 3) { "Invalid Token format" }
        encodedHeader = if (encodedHeader == parts[0]) {
            parts[0]
        } else {
            throw NoSuchAlgorithmException("JWT Header is Incorrect: " + parts[0])
        }
        payload = JSONObject(decode(parts[1]))
        if (payload.equals(null)) {
            throw JSONException("Payload is Empty: ")
        }
        if (!payload.has("exp")) {
            throw JSONException("Payload doesn't contain expiry $payload")
        }
        signature = parts[2]
    }

    override fun toString(): String {
        return encodedHeader + "." + encode(payload) + "." + signature
    }

    fun isValid(): Boolean {
        return (payload.getLong("exp") > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) //token not expired
//                && signature == hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY) //signature matched
                && signature == hmacSha256(encode(payload), SECRET_KEY) //signature matched
                )
    }

    fun getSubject(): String? {
        return payload.getString("sub")
    }

    fun getAudience(): List<String>? {
        val arr = payload.getJSONArray("aud")
        val list: MutableList<String> = ArrayList()
        for (i in 0 until arr.length()) {
            list.add(arr.getString(i))
        }
        return list
    }

    fun encode(obj: JSONObject): String {
        return encode(obj.toString().toByteArray(StandardCharsets.UTF_8)).toString()
    }

    fun encode(bytes: ByteArray): String? {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    fun decode(encodedString: String): String? {
        return String(Base64.getUrlDecoder().decode(encodedString));
    }


    fun hmacSha256(data: String, secret: String): String? {
        return try {

            //MessageDigest digest = MessageDigest.getInstance("SHA-256")
            val hash: ByteArray = secret.toByteArray(StandardCharsets.UTF_8) //digest.digest(secret.getBytes(StandardCharsets.UTF_8))
            val sha256Hmac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(hash, "HmacSHA256")
            sha256Hmac.init(secretKey).toString()
            return sha256Hmac.doFinal(data.toByteArray()).toString()


        } catch (ex: NoSuchAlgorithmException) {
            Logger.getLogger(JWebToken()::class.java.getName()).log(Level.SEVERE, ex.message, ex)
            null
        } catch (ex: InvalidKeyException) {
            Logger.getLogger(JWebToken()::class.java.getName()).log(Level.SEVERE, ex.message, ex)
            null
        }
    }
}