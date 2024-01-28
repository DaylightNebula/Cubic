package daylightnebula.cubic.fabric.screens

import daylightnebula.cubic.core.CubicProperties
import daylightnebula.cubic.fabric.utils.Files
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.io.File

class CreateInstanceScreen(
    val template: File,
    val templateProperties: CubicProperties
): Screen(Component.literal("CreateInstanceScreen")) {
    private lateinit var textBox: EditBox
    private var text: String = ""

    override fun init() {
        textBox = EditBox(minecraft!!.font, width / 2 - 100, 96, 200, 20, Component.literal("Name this world..."))
        textBox.setMaxLength(128)
        textBox.setResponder { text = it }
        addWidget(textBox)

        // create button
        addRenderableWidget(
            Button.builder(Component.literal("Create")) {
                // copy template
                val targetFolder = File(Files.instancesFolder, text)
                targetFolder.mkdirs()
                template.copyRecursively(targetFolder)

                // update properties
                templateProperties.name = text
                val propFile = File(targetFolder, "cubic.properties")
                propFile.writeText(templateProperties.toString())

                // start
                minecraft?.setScreen(LoadScreen(targetFolder))
            }.bounds(width / 2 - 52, height / 2, 50, 20)
                .build()
        )

        // cancel button
        addRenderableWidget(
            Button.builder(CommonComponents.GUI_CANCEL) { minecraft?.setScreen(SelectGameScreen()) }
                .bounds(width / 2 + 2, height / 2, 50, 20)
                .build()
        )
    }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(guiGraphics, i, j, f)
        textBox.render(guiGraphics, i, j, f)
    }
}