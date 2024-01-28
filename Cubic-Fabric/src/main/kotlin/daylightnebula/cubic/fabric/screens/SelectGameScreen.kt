package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.fabric.utils.WidgetElement
import daylightnebula.cubic.fabric.utils.WidgetUser
import daylightnebula.cubic.fabric.utils.list
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class SelectGameScreen(
    private val previous: Screen
): WidgetUser, Screen(Component.literal("SelectGame")) {
    override fun <T : WidgetElement> addWidget(widget: T): T = addRenderableWidget(widget)

    override fun init() {
        list(minecraft!!, listOf("Test"), 200, height - 83, 32, 36, width / 2 - 4 - 200) { context, data ->
            context.gui.drawString(
                context.minecraft.font,
                Component.literal(data).withStyle(ChatFormatting.WHITE),
                context.x + context.width / 2, context.y,
                567456
            )
        }

        list(minecraft!!, listOf("TestGame"), 200, height - 83, 32, 36, width / 2 + 4) { context, data ->
            context.gui.drawString(
                context.minecraft.font,
                Component.literal(data).withStyle(ChatFormatting.WHITE),
                context.x + (context.width / 2) - (context.minecraft.font.width(data) / 2), context.y,
                567456
            )
        }
    }
}
