package daylightnebula.cubic.fabric.screens

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.io.File

class ConfirmDeleteScreen(
    val toDelete: File,
    val after: () -> Unit
): Screen(Component.literal("ConfirmDeleteScreen")) {
    override fun init() {
        // delete button
        addRenderableWidget(
            Button.builder(Component.literal("Delete")) { toDelete.deleteRecursively(); after() }
                .bounds(width / 2 - 52, height / 2, 50, 20)
                .build()
        )

        // cancel button
        addRenderableWidget(
            Button.builder(CommonComponents.GUI_CANCEL) { after() }
                .bounds(width / 2 + 2, height / 2, 50, 20)
                .build()
        )
    }

    override fun render(gui: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(gui, i, j, f)

        // draw loading text
        gui.drawCenteredString(
            minecraft!!.font,
            Component.literal("Delete ${toDelete.name}?").withStyle(ChatFormatting.WHITE),
            width / 2,
            height / 2 - 20,
            12345
        )
    }
}