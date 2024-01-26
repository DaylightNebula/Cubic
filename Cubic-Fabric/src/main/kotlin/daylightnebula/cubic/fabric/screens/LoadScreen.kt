package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.fabric.loader.CubicLoader
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.screens.ConnectScreen
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.multiplayer.ServerData
import net.minecraft.client.multiplayer.resolver.ServerAddress
import net.minecraft.network.chat.Component
import java.io.File

class LoadScreen(val file: File): Screen(Component.literal("Load")) {
    override fun init() {
        super.init()
        CubicLoader.loadInto(minecraft!!, file).whenComplete { result, _ ->
            println("Started with result $result")

            minecraft?.execute {
                ConnectScreen.startConnecting(
                    this,
                    minecraft!!,
                    ServerAddress.parseString("localhost"),
                    ServerData("Cubic Server", "Cubic server.", ServerData.Type.OTHER),
                    false
                )
            }
        }
    }

    override fun render(gui: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(gui, i, j, f)

        // draw loading text
        gui.drawCenteredString(
            minecraft!!.font,
            Component.literal("Loading...").withStyle(ChatFormatting.WHITE),
            width / 2,
            height / 2,
            12345
        )
    }
}