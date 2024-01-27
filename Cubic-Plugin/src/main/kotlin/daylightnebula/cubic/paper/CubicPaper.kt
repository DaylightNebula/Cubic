package daylightnebula.cubic.paper

import daylightnebula.cubic.core.getEmptyPort
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class CubicPaper: JavaPlugin(), Listener {
    private val singleplayerPort = System.getProperty("singleplayer")?.toInt()

    // on load, start communicator
    override fun onLoad() {
        PaperCommunicator.start(getEmptyPort())
    }

    // on enable, if single player port was given, send ready request to that port
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        if (singleplayerPort != null)
            PaperCommunicator.ready("localhost:$singleplayerPort")
    }

    // on disable, stop communicator
    override fun onDisable() {
        PaperCommunicator.stop()
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) { Bukkit.shutdown() }
}