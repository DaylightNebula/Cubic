package daylightnebula.cubic.paper

import daylightnebula.cubic.core.getEmptyPort
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class CubicPaper: JavaPlugin(), Listener {
    private val singleplayerPort = System.getProperty("singleplayer")?.toInt()

    // on load, start communicator
    override fun onLoad() {
        PaperCommunicator.start(getEmptyPort())
    }

    override fun onEnable() {
        // register this as an event listener
        Bukkit.getPluginManager().registerEvents(this, this)

        // if single player port was given
        if (singleplayerPort != null) {
            // send ready request to that port
            PaperCommunicator.ready("localhost:$singleplayerPort")

            // after 2 minutes, if no player connected, stop
            Bukkit.getScheduler().runTaskLater(
                this,
                Runnable { if (Bukkit.getOnlinePlayers().isEmpty()) Bukkit.shutdown() },
                2400
            )
        }
    }

    // on disable, stop communicator
    override fun onDisable() {
        PaperCommunicator.stop()
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) { Bukkit.shutdown() }
}