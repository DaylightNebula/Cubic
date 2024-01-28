package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.core.CubicProperties
import daylightnebula.cubic.fabric.utils.*
import daylightnebula.cubic.fabric.utils.list
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.io.File

// todo create from template (w/ property name updated)
// todo delete template

class SelectGameScreen: WidgetUser, Screen(Component.literal("SelectGame")) {
    override fun <T : WidgetElement> addWidget(widget: T): T = addRenderableWidget(widget)

    override fun init() {
        renderList(
            minecraft!!,
            (Files.instancesFolder.listFiles()?.toList() ?: emptyList<File>()).mapNotNull {
                val propFile = it.listFiles()?.find { it.name == "cubic.properties" } ?: return@mapNotNull null
                it to CubicProperties.fromString(propFile.readText())
            },
            200, height - 83,
            32, 36,
            xOffset = width / 2 - 4 - 200
        ) { context, (file, properties) ->
            // text
            context.text(
                properties.name, ChatFormatting.WHITE,
                20, 0,
                minecraft!!.font.width(properties.name), 20
            )
            context.text(
                properties.template, ChatFormatting.DARK_GRAY,
                30 + minecraft!!.font.width(properties.name), 0,
                minecraft!!.font.width(properties.template), 20
            )

            // play button
            context.button(
                "Play", ChatFormatting.GREEN,
                context.x + context.width - 82, 0,
                40, 20
            ) { minecraft?.setScreen(LoadScreen(file)) }

            // delete button
            context.button(
                "X", ChatFormatting.RED,
                context.x + context.width - 38, 0,
                20, 20
            ) { minecraft?.setScreen(ConfirmDeleteScreen(file) { minecraft?.setScreen(SelectGameScreen()) }) }
        }

        renderList(
            minecraft!!,
            (Files.templatesFolder.listFiles()?.toList() ?: emptyList<File>()).mapNotNull {
                val propFile = it.listFiles()?.find { it.name == "cubic.properties" } ?: return@mapNotNull null
                it to CubicProperties.fromString(propFile.readText())
            },
            200, height - 83,
            32, 36,
            xOffset = width / 2 + 4
        ) { context, (file, properties) ->
            // text
            context.text(
                properties.template, ChatFormatting.WHITE,
                20, 0,
                minecraft!!.font.width(properties.template), 20
            )

            // play button
            context.button(
                "Create", ChatFormatting.WHITE,
                context.width - 82, 0,
                40, 20
            ) { minecraft?.setScreen(CreateInstanceScreen(file, properties)) }

            // delete button
            context.button(
                "X", ChatFormatting.RED,
                context.width - 38, 0,
                20, 20
            ) { minecraft?.setScreen(ConfirmDeleteScreen(file) { minecraft?.setScreen(SelectGameScreen()) }) }
        }

        // back button
        addRenderableWidget(
            Button.builder(CommonComponents.GUI_BACK) { minecraft?.setScreen(TitleScreen(false)) }
                .bounds(width / 2 - 50, height - 28, 100, 20)
                .build()
        )
    }
}
