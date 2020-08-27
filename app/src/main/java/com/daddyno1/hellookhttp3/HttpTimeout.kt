package com.daddyno1.hellookhttp3

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Okhttp使用体验
 */

// 推荐OkHttpClient保持单例，每一个client拥有连接池、线程池，重用这些资源可以减少延迟，节省资源。
private val client by lazy {
    OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
//        .connectTimeout(100, TimeUnit.SECONDS)
//        .callTimeout(100, TimeUnit.MILLISECONDS)
        .build()
}

/**
 * get 请求
 */
fun get2(url: String): String? {
    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()
    return response.body?.string()
}

/**
 * post 请求
 */
fun postJson2(url: String, json: String): String? {
    //需要多构造一个RequestBody，携带我们的数据，需要指定MediaType，用于描述传输数据的MIME类型
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    return response.body?.string()
}

fun postJsonEnqueue2(url: String, json: String, callback: Callback) {
    //需要多构造一个RequestBody，携带我们的数据，需要指定MediaType，用于描述传输数据的MIME类型
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    client.newCall(request).enqueue(callback)
}
