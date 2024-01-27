package daylightnebula.cubic.fabric

import daylightnebula.cubic.core.Communicator
import daylightnebula.cubic.core.CubicServerMetadata

object FabricCommunicator: Communicator() {
    private val readyFrom = mutableMapOf<String, CubicServerMetadata>()

    override val metadata: CubicServerMetadata? = null

    override fun receivedReady(address: String, metadata: CubicServerMetadata) {
        readyFrom[address] = metadata
    }

    fun resetReady() = readyFrom.clear()
    fun getReady(address: String) = readyFrom[address]
}