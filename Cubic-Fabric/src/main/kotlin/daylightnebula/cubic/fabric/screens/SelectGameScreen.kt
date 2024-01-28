package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.fabric.utils.*
import daylightnebula.cubic.fabric.utils.list
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.io.File

class SelectGameScreen(
    private val previous: Screen
): WidgetUser, Screen(Component.literal("SelectGame")) {
    override fun <T : WidgetElement> addWidget(widget: T): T = addRenderableWidget(widget)

    override fun init() {
        renderList(
            minecraft!!,
            Files.instancesFolder.listFiles()?.toList() ?: emptyList<File>(),
            200, height - 83,
            32, 36,
            xOffset = width / 2 - 4 - 200
        ) { context, file ->
            context.text(file.name, ChatFormatting.WHITE, 10, 0, 40, 20)
            context.button(
                "Play", ChatFormatting.GREEN,
                context.x + context.width - 82, 0,
                40, 20
            ) { minecraft?.setScreen(LoadScreen(file)) }
            context.button(
                "X", ChatFormatting.RED,
                context.x + context.width - 38, 0,
                20, 20
            ) {
                println("TODO Delete")
            }
        }

        renderList(minecraft!!, listOf("TestGame"), 200, height - 83, 32, 36, width / 2 + 4) { context, data ->
            context.gui.drawString(
                context.minecraft.font,
                Component.literal(data).withStyle(ChatFormatting.WHITE),
                context.x + (context.width / 2) - (context.minecraft.font.width(data) / 2), context.y,
                567456
            )
        }
    }
}
