package daylightnebula.cubic.fabric.utils

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.joml.Vector4i

class GuiButton(
    private val minecraft: Minecraft,
    private val text: String,
    private val format: ChatFormatting,
    private val onPress: () -> Unit
) {
    private val bounds = Vector4i()
    private var isHovered = false

    fun attemptPress() {
        if (isHovered) onPress()
    }

    fun render(gui: GuiGraphics, x: Int, y: Int, width: Int, height: Int, mx: Int, my: Int) {
        // update bounds
        bounds.set(x, y, width, height)

        // update is hovered
        isHovered = mx >= x && mx <= x + width && my >= y && my <= y + height

        // draw button background
        gui.blitSprite(
            ResourceLocation(
                if (isHovered) "widget/button_highlighted"
                else "widget/button"
            ),
            x, y,
            width, height
        )

        // draw centered text
        val textWidth = minecraft.font.width(text)
        val textHeight = minecraft.font.lineHeight
        gui.drawString(
            minecraft.font,
            Component.literal(text).withStyle(format),
            (width - textWidth) / 2 + x,
            (height - textHeight) / 2 + y,
            1123455
        )
    }
}