package daylightnebula.cubic.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap
import kotlinx.serialization.properties.encodeToStringMap

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
    var name: String = ""
) {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        fun fromString(str: String) =
            Properties.decodeFromStringMap<CubicProperties>(
                str.lines().associate {
                    val tokens = it.split("=", limit = 2)
                    tokens.first() to tokens.last()
                }
            )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun toString() =
        Properties.encodeToStringMap(this)
            .entries.joinToString(separator = "\n") { "${it.key}=${it.value}" }
}
