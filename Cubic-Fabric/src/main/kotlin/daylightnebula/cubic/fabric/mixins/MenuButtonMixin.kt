package daylightnebula.cubic.fabric.mixins

import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import java.util.*

@Mixin(TitleScreen::class)
class MenuButtonMixin: Screen(Component.literal("Connect Button")) {
    @Inject(
        method = ["createNormalMenuOptions"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
            ordinal = 1,
            shift = At.Shift.BEFORE
        )],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun injectCustomButton(
        a: Int,
        b: Int,
        info: CallbackInfo,
        multiplayerDisabledReason: Component,
        multiplayerDisabled: Boolean,
        multiplayerDisabledTooltip: Tooltip
    ) {
        // generate connect text and get its width
        val text: Component = Component.literal("Connect")
        val width = Mth.clamp(Objects.requireNonNull(minecraft)!!.font.width(text), 50, width - (width / 2 + 108))

        // create and add button
        addRenderableWidget(
            Button.builder(text) { button -> println("Test") }
                .bounds(this.width / 2 + 104, a + b, width, 20)
                .tooltip(multiplayerDisabledTooltip)
                .build()
        )
    }
}