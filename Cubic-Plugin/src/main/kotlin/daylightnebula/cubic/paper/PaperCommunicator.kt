package daylightnebula.cubic.paper

import daylightnebula.cubic.core.Communicator
import daylightnebula.cubic.core.CubicProperties
import daylightnebula.cubic.core.CubicServerMetadata
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromMap
import org.bukkit.Bukkit
import java.io.File

object PaperCommunicator: Communicator() {
    // load cubic.properties into a CubicProperties object
    @OptIn(ExperimentalSerializationApi::class)
    val properties: CubicProperties = Properties.decodeFromMap(
        File("cubic.properties").readLines().associate {
            val tokens = it.split("=", limit = 2)
            tokens.first() to tokens.last()
        }
    )

    override val metadata: CubicServerMetadata = CubicServerMetadata(properties.name, properties.template, port = Bukkit.getPort())

    override fun receivedReady(address: String, metadata: CubicServerMetadata) {}
}