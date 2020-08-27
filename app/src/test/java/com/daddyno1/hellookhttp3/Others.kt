package com.daddyno1.hellookhttp3

import org.junit.Test

class Others {
    @Test
    fun testIODic(): Unit {
        //所有在Java.io中的类都将相对路径名解释为以用户工作目录开始。 工作目录可以通过这个来查看：
        println(System.getProperty("user.dir"))   // /Users/jxf/workspace/Android/projects/HelloOkhttp3/app

        val a = 3
        val res = when {
            a == 3 -> fun1()
            else -> fun2()
        }

        println(res)
    }

    fun fun1(): Int {
        return 33
    }

    fun fun2(): Int {
        return 44
    }
}