package daylightnebula.cubic.fabric

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

class CubicFabric: ModInitializer {
    override fun onInitialize() {
        // setup communicator
        FabricCommunicator.start()
        ServerLifecycleEvents.SERVER_STOPPING.register { server ->
            FabricCommunicator.stop()
        }

        println("Cubic setup!")
    }
}