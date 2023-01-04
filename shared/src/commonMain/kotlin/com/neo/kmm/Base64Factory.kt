package com.neo.kmm

expect object Base64Factory {
    fun createEncoder(): Base64Encoder
}