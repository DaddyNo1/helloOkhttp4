package com.daddyno1.hellookhttp3.example

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.IOException
import org.junit.Test
import kotlin.system.exitProcess


/**
 * 该目录下的代码运行在本地JVM上，其优点是速度快，不需要设备或模拟器的支持，但是无法直接运行含有android系统API引用的测试代码。
 */
class HttpTest {

    @Test
    fun test() {
        val server = MockWebServer()
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/v1/login/auth" -> MockResponse().setResponseCode(200)
                "/v1/check/version" -> MockResponse().setResponseCode(200).setBody("version=9")
                "/v1/profile/info" -> MockResponse().setResponseCode(200)
                    .setBody("{\"info\":{\"name\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}")
                else -> MockResponse().setResponseCode(404)
            }
        }
        server.dispatcher = dispatcher

        try {
            server.start(8099)
        } catch (e: IOException) {
            e.printStackTrace()
            exitProcess(0)
        }

        //轮训
        while (true) {
            val request = server.takeRequest()
            println("request.path  ->")
            println("${request.path} \n")

            println("request.headers  ->")
            println("${request.headers} \n")

            println("request.requestLine  ->")
            println("${request.requestLine} \n")

            println("request.requestUrl  ->")
            println("${request.requestUrl} \n")

            println("request.body  ->")
            println("${request.body} \n")

            println("request.sequenceNumber  ->")
            println("${request.sequenceNumber} \n")

            println("request.tlsVersion  ->")
            println("${request.tlsVersion} \n")
        }
    }

}