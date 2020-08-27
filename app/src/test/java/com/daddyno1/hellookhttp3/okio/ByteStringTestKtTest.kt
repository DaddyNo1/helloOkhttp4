package com.daddyno1.hellookhttp3.okio

import org.junit.Test

import org.junit.Assert.*
import java.io.File

class ByteStringTestKtTest {

    @Test
    fun getMD5Test() {
        println(getMD5("abc"))
    }

    @Test
    fun getMD5File(){
        println(System.getProperty("user.dir"))
        println(getMD5(File("pic.png")))
    }
}