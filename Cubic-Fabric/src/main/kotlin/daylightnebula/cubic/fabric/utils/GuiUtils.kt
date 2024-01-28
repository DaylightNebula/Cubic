package daylightnebula.cubic.fabric.utils

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

// context to contain variables provided by Minecraft for rendering
data class RenderContext(
    val minecraft: Minecraft, val gui: GuiGraphics,
    val x: Int, val y: Int,
    val width: Int, val height: Int,
    val mouseX: Int, val mouseY: Int,
    val hovered: Boolean, val clicked: Boolean,
    val delta: Float
) {
    fun text(
        text: String,
        formatting: ChatFormatting,
        x: Int, y: Int,
        width: Int, height: Int,
    ) {
        // draw centered text
        val textWidth = minecraft.font.width(text)
        val textHeight = minecraft.font.lineHeight
        gui.drawString(
            minecraft.font,
            Component.literal(text).withStyle(formatting),
            (width - textWidth) / 2 + this.x + x,
            (height - textHeight) / 2 + this.y + y,
            1123455
        )
    }

    fun button(
        text: String,
        formatting: ChatFormatting,
        x: Int, y: Int,
        width: Int, height: Int,
        onPress: () -> Unit
    ) {
        // update is hovered
        val isHovered =
            mouseX >= this.x + x &&
            mouseX <= this.x + x + width &&
            mouseY >= this.y + y &&
            mouseY <= this.y + y + height

        if (isHovered && clicked) onPress()

        // draw button background
        gui.blitSprite(
            ResourceLocation(
                if (isHovered) "widget/button_highlighted"
                else "widget/button"
            ),
            this.x + x, this.y + y,
            width, height
        )

        // text
        text(text, formatting, x, y, width, height)
    }
}
