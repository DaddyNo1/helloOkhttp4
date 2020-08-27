package com.daddyno1.hellookhttp3.okio

import okio.ByteString
import okio.ByteString.Companion.encode
import okio.ByteString.Companion.toByteString
import okio.buffer
import okio.source
import java.io.File

/**
 * okio 中 ByteString 是字节串。方法声明在 okio.ByteString.kt 中，大多数具体实现都是以扩展方法的形式出现：internal/ByteString.kt
 * 所有能转换成 ByteString 的其他类型的扩展方法都写在  ByteString 类内 companion object 中进行扩展：如 String、InputStream、ByteBuffer等
 */

/**
 * ByteString 是一个不可变字节串，提供了针对字节串的一些列操作，可以类比字符串，方便了我们对于字节串的操作。
 */


//  okio精讲：  https://www.jianshu.com/p/84f1f4152124

//      https://www.jianshu.com/p/dcfa721daea6  超时机制    https://blog.csdn.net/weixin_33979363/article/details/88912020
//      https://www.jianshu.com/p/f1723dfc0a9d   https://www.cnblogs.com/luochengor/archive/2011/08/11/2134818.html  守护线程


// 查看一个 String 是md5值，最终以字节的十六进制字符串表示
fun getMD5(str: String): String{
    val byteString = str.encode()
    return byteString.md5().hex()
}

// 查看一个文件的MD5值，最终以字节的十六进制字符串表示
fun getMD5(file: File): String{
    file.source().buffer().use {
        return it.readByteArray().toByteString().md5().hex()
    }
}