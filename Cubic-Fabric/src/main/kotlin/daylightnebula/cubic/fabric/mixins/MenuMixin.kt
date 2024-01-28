package daylightnebula.cubic.fabric.mixins

import com.mojang.realmsclient.RealmsMainScreen
import daylightnebula.cubic.fabric.screens.PickGameScreen
import daylightnebula.cubic.fabric.screens.SelectGameScreen
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen
import net.minecraft.network.chat.Component
import org.spongepowered.asm.mixin.Mixin

@Mixin(TitleScreen::class)
class MenuMixin: Screen(Component.literal("Connect Button")) {
    @Override
    fun createNormalMenuOptions(a: Int, b: Int) {
        // single player button
        addRenderableWidget(
            Button.builder(Component.translatable("menu.singleplayer")) { minecraft?.setScreen(SelectWorldScreen(this)) }
                .bounds(width / 2 - 100, a, 98, 20)
                .build()
        )

        // multiplayer button
        addRenderableWidget(
            Button.builder(Component.translatable("menu.multiplayer")) { minecraft?.setScreen(JoinMultiplayerScreen(this)) }
                .bounds(width / 2 - 100, a + b, 200, 20)
                .build()
        )

        // realms button
        addRenderableWidget(
            Button.builder(Component.translatable("menu.online")) { minecraft?.setScreen(RealmsMainScreen(this)) }
                .bounds(width / 2 - 100, a + b * 2, 200, 20)
                .build()
        )

        // create and add button
        addRenderableWidget(
            Button.builder(Component.literal("Games")) { minecraft?.setScreen(SelectGameScreen()) }
                .bounds(this.width / 2 + 2, a, 98, 20)
                .build()
        )
    }
}