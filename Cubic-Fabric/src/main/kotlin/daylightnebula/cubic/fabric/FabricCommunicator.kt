package daylightnebula.cubic.fabric

import daylightnebula.cubic.core.Communicator
import daylightnebula.cubic.core.CubicServerMetadata

object FabricCommunicator: Communicator() {
    override val metadata: CubicServerMetadata? = null

    override fun receivedReady(address: String, metadata: CubicServerMetadata) {
        println("TODO received ready from $address $metadata")
    }
}