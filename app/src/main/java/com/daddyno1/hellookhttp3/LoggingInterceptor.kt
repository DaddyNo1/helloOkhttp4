package com.daddyno1.hellookhttp3

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val start = System.currentTimeMillis()
        println("start:  $start")
        val request = chain.request()
        //之后的会阻塞，知道网络请求成功返回以后，才会继续执行。其实是 proceed 这个方法会阻塞
        val response = chain.proceed(request)


        println("during: ${System.currentTimeMillis() - start}  ms")
        return response
    }
}