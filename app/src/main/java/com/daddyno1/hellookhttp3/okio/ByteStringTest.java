package com.daddyno1.hellookhttp3.okio;

import kotlin.text.Charsets;
import okio.ByteString;

public class ByteStringTest {

    public void fun() {
        ByteString byteString = ByteString.encodeString("abc", Charsets.UTF_8);
    }
}
