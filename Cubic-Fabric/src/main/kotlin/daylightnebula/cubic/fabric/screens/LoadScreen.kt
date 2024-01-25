package daylightnebula.cubic.fabric.screens

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.io.File

class LoadScreen(val file: File): Screen(Component.literal("Load")) {
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