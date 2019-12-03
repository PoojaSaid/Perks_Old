package com.example.demo.Table


import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.util.EntityUtils
import org.apache.http.entity.ByteArrayEntity
import java.util.Map


object PushyAPI {
    var mapper = ObjectMapper()
    // Insert your Secret API Key here
    const val SECRET_API_KEY = "8e50ec172970a8876baa3c01c03168f3361b659bcddab082b1857365c1b8d806"

    @Throws(Exception::class)
    fun sendPush(req: PushyPushRequest?) {

        // Get custom HTTP client
        val client: HttpClient = DefaultHttpClient()
        // Create POST request
        val request = HttpPost("https://api.pushy.me/push?api_key=$SECRET_API_KEY")
        // Set content type to JSON
        request.addHeader("Content-Type", "application/json")
        // Convert post data to JSON
        val json = mapper.writeValueAsBytes(req)
        // Send post data as byte array
        request.setEntity(ByteArrayEntity(json))
        // Execute the request
        val response: HttpResponse = client.execute(request, BasicHttpContext())
        // Get response JSON as string
        val responseJSON: String = EntityUtils.toString(response.getEntity())
        // Convert JSON response into HashMap

        val map: MutableMap<*, *>? = mapper.readValue<MutableMap<*, *>>(responseJSON, MutableMap::class.java)

        // Got an error?
//        if (map.containsKey("error")) {
//            // Throw it
//            throw Exception(map["error"].toString())
//        }
    }

    class PushyPushRequest(var data: Any, var to: Any, var notification: Any)

    fun sendSamplePush() { // Prepare list of target device tokens
        val deviceTokens: MutableList<String> = ArrayList()
        // Add your device tokens here
        deviceTokens.add("cdd92f4ce847efa5c7f")
        // Convert to String[] array
        val to = deviceTokens.toTypedArray()
        // Optionally, send to a publish/subscribe topic instead
        // String to = '/topics/news';
        // Set payload (any object, it will be serialized to JSON)
        val payload: MutableMap<String, String> = HashMap()
        // Add "message" parameter to payload
        payload["message"] = "Hello World!"
        // iOS notification fields
        val notification: MutableMap<String, Any> = HashMap()
        notification["badge"] = 1
        notification["sound"] = "ping.aiff"
        notification["body"] = "Hello World \u270c"
        // Prepare the push request

        val push = PushyPushRequest(payload, to, notification)
        try {
            // Try sending the push notification
            sendPush(push)
        } catch (exc: Exception) {
            // Error, print to console
            println(exc.toString())
        }
    }
}

