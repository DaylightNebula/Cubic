package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.fabric.utils.Files
import daylightnebula.cubic.fabric.utils.GuiButton
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.ObjectSelectionList
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.io.File

class PickGameScreen(val previous: Screen): Screen(Component.literal("PickGame")) {
    override fun init() {
        // add list
        addRenderableWidget(
            GameList(minecraft!!, Files.instancesFolder, width, height - 112, 48, 36)
        )

        // create button
        addRenderableWidget(
            Button.builder(Component.literal("Create")) { minecraft?.setScreen(previous) }
                .bounds(width / 2 - 100, height - 28, 148, 20)
                .build()
        )

        // back button
        addRenderableWidget(
            Button.builder(CommonComponents.GUI_BACK) { minecraft?.setScreen(previous) }
                .bounds(width / 2 + 50, height - 28, 50, 20)
                .build()
        )
    }
}

class GameList(
    minecraft: Minecraft,
    instancesFolder: File,
    x: Int, y: Int,
    width: Int, height: Int
): ObjectSelectionList<GameList.Entry>(minecraft, x, y, width, height) {
    init {
        // for each instance in the instance folder, add an entry for that
        instancesFolder.listFiles()?.forEach { file ->
            addEntry(GameListEntry(minecraft, file))
        }
    }

    abstract class Entry: AutoCloseable, ObjectSelectionList.Entry<Entry>() {
        override fun close() {}
    }
    data class GameListEntry(val minecraft: Minecraft, val file: File): Entry() {
        // buttons
        private val playButton = GuiButton(minecraft, "Play", ChatFormatting.GREEN) { minecraft.setScreen(LoadScreen(file)) }
        private val deleteButton = GuiButton(minecraft, "X", ChatFormatting.RED) { println("TODO delete") }

        // render element
        override fun render(
            gui: GuiGraphics,
            idx: Int,
            y: Int,
            x: Int,
            width: Int,
            height: Int,
            mouseX: Int,
            mouseY: Int,
            hovered: Boolean,
            tickDelta: Float
        ) {
            gui.drawString(
                minecraft.font,
                Component.literal(file.name).withStyle(ChatFormatting.WHITE),
                x, (height - minecraft.font.lineHeight) / 2 + y,
                567456
            )

            playButton.render(gui, x + width - 74, (height - 20) / 2 + y, 40, 20, mouseX, mouseY)
            deleteButton.render(gui, x + width - 30, (height - 20) / 2 + y, 20, 20, mouseX, mouseY)
        }

        // attempt tp click buttons
        override fun mouseClicked(mx: Double, my: Double, i: Int): Boolean {
            playButton.attemptPress()
            deleteButton.attemptPress()
            return super.mouseClicked(mx, my, i)
        }

        override fun getNarration(): Component = Component.literal("Game save")
    }
}
