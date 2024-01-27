package daylightnebula.cubic.fabric

import daylightnebula.cubic.core.getEmptyPort
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

class CubicFabric: ModInitializer {
    override fun onInitialize() {
        // setup communicator
        FabricCommunicator.start(getEmptyPort())
        ServerLifecycleEvents.SERVER_STOPPING.register { server ->
            FabricCommunicator.stop()
        }

        println("Cubic setup!")
    }
}