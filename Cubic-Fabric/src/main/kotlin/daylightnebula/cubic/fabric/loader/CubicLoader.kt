package daylightnebula.cubic.fabric.loader

import daylightnebula.cubic.core.getEmptyPort
import daylightnebula.cubic.fabric.FabricCommunicator
import net.minecraft.client.Minecraft
import java.io.File
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

object CubicLoader {
    fun loadInto(minecraft: Minecraft, folder: File): CompletableFuture<Result<Int>> {
        println("Loading properties...")
        // load properties
        val propFile = File(folder, "cubic.properties")
        if (!propFile.exists()) return CompletableFuture.completedFuture(Result.failure(IllegalStateException("Properties file doesn't exist")))
        val properties = propFile.readLines().mapNotNull {
            if (it.startsWith("#") || !it.contains("=")) return@mapNotNull null
            val tokens = it.split("=", limit = 2)
            tokens.first() to tokens.last()
        }.toMap()

        println("Starting...")
        // start server using properties start command
        val command = properties["start"] ?: return CompletableFuture.completedFuture(Result.failure(IllegalStateException("No start command")))
        val thread = thread {
            ProcessBuilder(command.replace("{}", "-Dsingleplayer=${FabricCommunicator.port}").split(" "))
                .directory(folder)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
        }

        // reset ready list in communicator
        FabricCommunicator.resetReady()

        // start final connect
        return CompletableFuture.supplyAsync {
            // get current start time
            val startTime = System.currentTimeMillis()

            // wait until getReady does not return null
            while (FabricCommunicator.getReady("127.0.0.1") == null) {
                // if 2 minutes have passed, fail
                if (System.currentTimeMillis() - startTime > 120000) return@supplyAsync Result.failure(IllegalStateException("Timeout"))

                // sleep so we don't waste resources
                sleep(100)
            }

            // send pack port
            Result.success(FabricCommunicator.getReady("127.0.0.1")!!.port)
        }
    }
}