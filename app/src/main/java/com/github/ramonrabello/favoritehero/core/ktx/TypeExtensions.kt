package com.github.ramonrabello.favoritehero.core.ktx

import java.security.NoSuchAlgorithmException

/**
 * Generates a MD5 representation
 * for the underlying String.
 */
fun String.toMD5(): String {
    try {
        // Create MD5 Hash
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(this.toByteArray())
        return digest.digest().toHex()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * Converts a [ByteArray] to its hexadecimal
 * representation.
 */
fun ByteArray.toHex() : String {
    val stringBuilder = StringBuilder()
    for (byte in this) {
        stringBuilder.append(String.format("%02x", byte))
    }
    return stringBuilder.toString()
}