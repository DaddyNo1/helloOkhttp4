package com.daddyno1.hellookhttp3.okio

import okio.Buffer
import okio.Source

fun main() {
    val buffer = Buffer()
    buffer.writeUtf8("hello world\n")
    buffer.writeInt(23)

    println(buffer.size)
    println(buffer.readUtf8Line())
    println(buffer.size)
    println(buffer.readInt())
    println(buffer.size)

    val a = "abc"
    println(a[0].toInt())



//    okio.Buffer
//    override fun writeAll(source: Source): Long = commonWriteAll(source)
}