package org.artificery.dramasukiutil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform