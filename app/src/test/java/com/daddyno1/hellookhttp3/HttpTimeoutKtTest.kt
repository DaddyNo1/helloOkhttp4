package com.daddyno1.hellookhttp3

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

class HttpTimeoutKtTest {

    private val server = MockWebServer()
    private val port = 8099


    @Before
    fun before() {
        println("---before---")
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/get" -> {
                    handleRequest(request)
                    TimeUnit.SECONDS.sleep(30)
                    MockResponse().setResponseCode(200).setBody("hello world")
                }
                "/postEnqueue" -> {
                    handleRequest(request)

                    TimeUnit.SECONDS.sleep(30)

                    MockResponse().setResponseCode(200).setBody("{\"a\":\"CC\"")
                }
                else ->{
                    println(".........................")
                    println(request.path)
                    MockResponse().setResponseCode(404)
                }
            }
        }
        server.dispatcher = dispatcher
        server.start(port)
    }

    private fun handleRequest(request: RecordedRequest) {
        println("request ->")
        println("method:${request.method}")
        println("content-type:${request.headers["content-type"]}")
        println("body:${request.body.readUtf8()}")
        println()
    }

//    @After
//    fun after() {
//        println("---after---")
//        server.shutdown()
//    }

    @Test
    fun get2() {
        var str: String? = null
        try {
            val str = get2("http:localhost:8099/get")
        }catch (e: Exception){
            // do nothing
            e.printStackTrace()
        } finally {
            println("response -> $str")
        }
    }

    @Test
    fun postJsonEnqueue2() {
        postJsonEnqueue2("http:localhost:8099/postEnqueue", "{\"a\":\"AA\",\"b\":\"BB\"}", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("onFailure - $e")
                println("thread: ${Thread.currentThread().name}")
                throw e
            }

            override fun onResponse(call: Call, response: Response) {
                println("response -> ${response.body?.string()}")
                println("thread: ${Thread.currentThread().name}")
            }
        })

        while (true){

        }
    }
}