package com.example.Looksy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform