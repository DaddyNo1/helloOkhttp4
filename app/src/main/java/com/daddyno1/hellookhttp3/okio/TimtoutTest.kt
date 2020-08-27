package com.daddyno1.hellookhttp3.okio

import okio.AsyncTimeout
import java.lang.Exception
import java.util.concurrent.TimeUnit

open class SomeClass {
    @Volatile var isInterrupted = false

    open fun doSomethingBlock() {
        println("start~")
        while (true) {
            if (isInterrupted) {
                throw InterruptedException("interrupted")
            }
        }
    }
}

class SomeClassAsyncTimeout(val obj: SomeClass) : AsyncTimeout() {
    override fun timedOut() {
        println("this operation is timed out")
        println("timedOut() 所在的线程： ${Thread.currentThread().name}")
        obj.isInterrupted = true
    }

    fun delegate(): SomeClass {
        return object : SomeClass() {
            // 先执行代理方法
            override fun doSomethingBlock() {
                withTimeout { obj.doSomethingBlock() }
            }
        }
    }
}


fun main() {
    val someObj = SomeClassAsyncTimeout(SomeClass())
        .apply { timeout(4, TimeUnit.SECONDS) }
        .delegate()

    try{
        someObj.doSomethingBlock()
    }catch (e: Exception){
        e.printStackTrace()
        println("已经超时")
    }
    println("main - end")
}