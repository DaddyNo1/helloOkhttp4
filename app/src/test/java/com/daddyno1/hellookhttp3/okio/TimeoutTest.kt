package com.daddyno1.hellookhttp3.okio

import android.text.format.Time
import okio.*
import org.junit.Test
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class TimeoutTest {
    @Test
    fun timeoutTest() {
        val buffer: Buffer = Buffer()
        val input = File("../pic/pic1.png").source()
        input.timeout().deadline(2, TimeUnit.NANOSECONDS)
        input.use {
            try {
                input.read(buffer, 100)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        println("the end")
    }

    @Test
    fun timeoutTest2() {
        val buffer = Buffer()
        val timeout = object : AsyncTimeout() {
            override fun timedOut() {
                println("timeoutTest2 - this operation is timed out")
            }
        }.apply {
            timeout(2, TimeUnit.SECONDS)
        }

        val source = timeout.source(File("../pic/pic1.png").source())
        source.use {
            source.read(buffer, 1024)
        }
    }

    @Test
    fun timeoutTest3() {
        val executors = Executors.newCachedThreadPool()
        println("1: ${Thread.currentThread().name}")

        val run1 = object : Runnable{
            override fun run() {
                println("2: ${Thread.currentThread().name}" )
                val thread = Thread.currentThread()

                val timeoutUtils = object : AsyncTimeout() {
                    fun someFunWasteTime(){
                        return withTimeout {
                            while(true){
                                if (Thread.currentThread().isInterrupted){
                                    break
                                }
                            }
                            println("done~")
                        }
                    }
                    override fun timedOut() {
                        println("3: this operation is timed out")
                        println("4: ${Thread.currentThread().name}")
                        thread.interrupt()
                    }
                }.apply {
                    timeout(5, TimeUnit.SECONDS)
                }

                try {
                    timeoutUtils.someFunWasteTime()
                }catch (e: IOException){
//                    e.printStackTrace()
                }
            }
        }
        executors.execute(run1)

        while (true){

        }
    }
}