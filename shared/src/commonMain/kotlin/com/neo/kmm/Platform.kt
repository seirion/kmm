package com.neo.kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform