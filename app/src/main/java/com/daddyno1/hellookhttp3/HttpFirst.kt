package com.daddyno1.hellookhttp3

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Okhttp代码初体验
 */

private val client by lazy {
    //为了测试post异步超市，固超时时间设置的很短
    OkHttpClient.Builder()
        .readTimeout(100, TimeUnit.MILLISECONDS)
        .writeTimeout(100, TimeUnit.MILLISECONDS)
        .connectTimeout(100, TimeUnit.MILLISECONDS)
        .callTimeout(100, TimeUnit.MILLISECONDS)
        .build()
}


val JSON = "application/json; charset=utf-8".toMediaType()
val PNG = "image/png; charset=utf-8".toMediaType()

/**
 * get 请求
 */
fun get(url: String): String? {
    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()
    return response.body?.string()
}

/**
 * post 请求
 */
fun postJson(url: String, json: String): String? {
    //需要多构造一个RequestBody，携带我们的数据，需要指定MediaType，用于描述传输数据的MIME类型
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    return response.body?.string()
}

fun postJsonEnqueue(url: String, json: String, callback: Callback) {
    //需要多构造一个RequestBody，携带我们的数据，需要指定MediaType，用于描述传输数据的MIME类型
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    client.newCall(request).enqueue(callback)
}

/**
 * form表单
 */
fun postForm(url: String, map: Map<String, String>): String? {
    //构建Form表单的RequestBody内容
    val formBuilder = FormBody.Builder()
    map.forEach {
        formBuilder.add(it.key, it.value)
    }
    val request = Request.Builder()
        .url(url)
        .post(formBuilder.build())
        .build()
    val response = client.newCall(request).execute()
    return response.body?.string()
}

/**
 * 上传图片
 */
fun postImage(url: String, file: File) {
    val body = file.asRequestBody(PNG)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    client.newCall(request).execute()
}

/**
 * 如果上传一张图片使用 RequestBody 也好，使用Multipart构建请求body都可以，但是如果遇到多张图片，或者图片和文本等其他混合的时候
 * 就要使用到 Multipart，它可以构建复杂的请求体
 */

/**
 * 图片和json混合请求。
 *  根据RFC 1867定义，我们需要选择一段数据作为“分割边界”（ boundary属性），这个“边界数据”不能在内容其他地方出现，
 *  一般来说使用一段从概率上说“几乎不可能”的数据即可。 不同浏览器的实现不同，例如火狐某次post的 boundary=—————————32404670520626 ，
 *  opera为boundary=———-E4SgDZXhJMgNE8jpwNdOAX ，每次post浏览器都会生成一个随机的30-40位长度的随机字符串，
 *  浏览器一般不会遍历这次post的所有数据找到一个不可能出现在数据中的字符串，这样代价太大了。一般都是随机生成，
 *  如果你遇见boundary值和post的内容一样，那样的话这次上传肯定失败，不过我建议你去买彩票，你太幸运了。Rfc1867这样说明
 *  {A boundary is selected that does not occur in any of the data. (This selection is sometimes done probabilisticly.)}。
 */
fun postMultipart(url: String, file: File) {
    /**
     * MultipartBody 可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。
     * 这些请求头可以用来描述这块请求，例如它的 Content-Disposition 。如果 Content-Length 和 Content-Type 可用的话，他们会被自动添加到请求头中。
     */
    val pngBody = file.asRequestBody(PNG)
    val body = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("a", "AAA")    //表单
        .addFormDataPart("b", "BBB")    //表单
        .addFormDataPart("file", "pic1.png", pngBody) //文件
        .build()
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    client.newCall(request).execute()
}

/**
 *
 *  multipart/mixed 和 multipart/form-date 都是多文件上传的格式。区别在于，multipart/form-data 是一种特殊的表单上传，
 *  其中普通字段的内容还是按照一般的请求体构建，文件字段的内容按照 multipart 请求体构建，后端在处理 multipart/form-data 请求的时候，
 *  会在服务器上建立临时的文件夹存放文件内容，可参看这篇文章。而 multipart/mixed 请求会将每个字段的内容，不管是普通字段还是文件字段，
 *  都变成 Stream 流的方式去上传，因此后端在处理 multipart/mixed 的内容时，必须从 Stream流中处理。
 *
 *  https://cloud.tencent.com/developer/article/1421488
 *
 */
fun postMultipartOther(url: String, file: File, json: String) {
    /**
     * MultipartBody 可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。
     * 这些请求头可以用来描述这块请求，例如它的 Content-Disposition 。如果 Content-Length 和 Content-Type 可用的话，他们会被自动添加到请求头中。
     */
    val pngBody = file.asRequestBody(PNG)
    val jsonBody = json.toRequestBody(JSON)

    //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。当Internet Explorer接收到头时，他会激活文件下载对话框，它的文件名框自动填充headers指定的文件名。
    val body = MultipartBody.Builder()
        .setType(MultipartBody.FORM)        //默认是 MIXED
        .addFormDataPart("json1", "json1", jsonBody)   // 这个请求你，会额外构建 Content-Disposition header信息。
        .addPart(jsonBody) // 这个请求体，除了body无额外header参数
        .addPart(Headers.headersOf("ke1", "self-defined header 1", "key2", "self-defined header 2"), pngBody)   //针对这个请求体，进行一些自定义的header参数
        .build()
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    client.newCall(request).execute()
}