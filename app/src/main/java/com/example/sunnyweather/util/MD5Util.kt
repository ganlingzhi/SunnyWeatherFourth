package com.example.sunnyweather.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MD5Util {
    fun encrypt(raw: String):String {
        var md5Str = raw
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(raw.toByte())
            val encryptContext = md.digest()
            val buf = StringBuffer("")
            var i = 0
            for (offset in 0..encryptContext.size) {
                i = encryptContext[offset].toInt()
                if (i < 0) {
                    i += 256
                }
                if (i < 16) {
                    buf.append("0")
                }
                buf.append(Integer.toHexString(i))
            }
            md5Str = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return md5Str.uppercase(Locale.getDefault())
    }
}