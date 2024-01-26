package daylightnebula.cubic.core

import kotlinx.serialization.Serializable

@Serializable
data class PingRequest(val time: Long)

@Serializable
data class PingResponse(val sentTime: Long, val receivedTime: Long)

@Serializable
data class CubicServerMetadata(
    val name: String,
    val template: String,
    val port: Int
)

@Serializable
data class CubicProperties(
    val start: String,
    val template: String,
    val name: String = ""
)
