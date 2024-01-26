package daylightnebula.cubic.fabric.loader

import kotlinx.coroutines.Job
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.TitleScreen
import java.io.File
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import javax.swing.text.html.HTML.Tag.P
import kotlin.concurrent.thread

object CubicLoader {
    fun loadInto(minecraft: Minecraft, folder: File): CompletableFuture<Boolean> {
        println("Loading properties...")
        // load properties
        val propFile = File(folder, "cubic.properties")
        if (!propFile.exists()) return CompletableFuture.completedFuture(false)
        val properties = propFile.readLines().mapNotNull {
            if (it.startsWith("#") || !it.contains("=")) return@mapNotNull null
            val tokens = it.split("=", limit = 2)
            tokens.first() to tokens.last()
        }.toMap()

        println("Starting...")
        // start server using properties start command
        val command = properties["start"] ?: return CompletableFuture.completedFuture(false)
        val thread = thread {
            ProcessBuilder(command.split(" "))
                .directory(folder)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
        }

        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(thread(start = false) { thread.join(100) })

        return CompletableFuture.supplyAsync {
            sleep(10000)
            true
        }
    }
}