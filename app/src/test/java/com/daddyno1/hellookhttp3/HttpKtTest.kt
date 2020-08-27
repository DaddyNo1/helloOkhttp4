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
import java.io.File
import java.io.IOException

/**
 * okhttp 单元测试类
 *
 * https://www.jianshu.com/p/da4a806e599b
 * https://blog.csdn.net/android_freshman/article/details/51910937
 *
 */
class HttpKtTest {
    private val server = MockWebServer()
    private val port = 8099

    @Before
    fun before() {
        println("---before---")
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/get" -> {
                    handleRequest(request)

                    MockResponse().setResponseCode(200).setBody("hello world")
                }
                "/post" -> {
                    handleRequest(request)

                    MockResponse().setResponseCode(200).setBody("{\"a\":\"CC\",\"b\":\"DD\"}")
                }
                "/postEnqueue" -> {
                    handleRequest(request)

                    Thread.sleep(200) //让请求端超时

                    MockResponse().setResponseCode(200).setBody("{\"a\":\"CC\"")
                }
                "/form" -> {
                    handleRequest(request)

                    MockResponse().setResponseCode(200).setBody("{\"a\":\"Yes\",\"b\":\"No\"}")
                }
                "/postPic" -> {
                    handleRequest(request)

                    MockResponse().setResponseCode(200)
                }
                "/postMultipart" -> {
                    handleRequest(request)

                    MockResponse().setResponseCode(200)
                }
                "/interceptor" -> {
                    handleRequest(request)
                    Thread.sleep(6000)

                    MockResponse().setResponseCode(200).setBody("{\"a\":\"CC\",\"b\":\"DD\"}")
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

    fun handleRequest(request: RecordedRequest) {
        println("request ->")
        println("method:${request.method}")
        println("content-type:${request.headers["content-type"]}")
        println("body:${request.body.readUtf8()}")
        println()
    }

    @After
    fun after() {
        println("---after---")
        server.shutdown()
    }

    @Test
    fun getTest() {
        println("--getTest--")
        println("thread: ${Thread.currentThread().name} \n")

        val str = get("http:localhost:8099/get")
        println("response -> $str")
    }

    @Test
    fun postJsonTest() {
        println("--postJsonTest--")
        println("thread: ${Thread.currentThread().name} \n")

        val str = postJson("http:localhost:8099/post", "{\"a\":\"AA\",\"b\":\"BB\"}")
        println("response -> $str")
    }

    @Test
    fun postJsonEnqueueTest() {
        println("--postJsonEnqueueTest--")
        println("thread: ${Thread.currentThread().name} \n")

        postJsonEnqueue("http:localhost:8099/postEnqueue", "{\"a\":\"AA\",\"b\":\"BB\"}", object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("onFailure - $e")
                println("thread: ${Thread.currentThread().name}")
            }

            override fun onResponse(call: Call, response: Response) {
                println("response -> ${response.body?.string()}")
                println("thread: ${Thread.currentThread().name}")
            }
        })

        Thread.sleep(1000)
    }

    @Test
    fun postFormTest() {
        println("--postFormTest--")
        println("thread: ${Thread.currentThread().name} \n")

        val map = mapOf("a" to "AAAA", "b" to "BBBB")
        val str = postForm("http:localhost:8099/form", map)
        println("response -> $str")
    }

    @Test
    fun postImageTest() {
        println("--postImageTest--")
        println("thread: ${Thread.currentThread().name} \n")
        postImage("http:localhost:8099/postPic", File("../pic/pic1.png"))
    }

    @Test
    fun postMultipartTest(){
        println("--postMultipart--")
        postMultipart("http:localhost:8099/postMultipart", File("../pic/pic1.png"))
    }

    @Test
    fun postMultipartOtherTest(){
        println("--postMultipart--")
        postMultipartOther("http:localhost:8099/postMultipart", File("../pic/pic1.png"), "{\"M\":\"MM\"}")
    }

    /**
     * 测试 interceptor 中代码执行的 intercept() 方法的阻塞，及相关回调
     */
    @Test
    fun testInterceptor() {
        println("--testInterceptor--")
        println("thread: ${Thread.currentThread().name} \n")

        val str = postJson("http:localhost:8099/interceptor", "{\"a\":\"AA\",\"b\":\"BB\"}")
        println("response -> $str")
    }
}