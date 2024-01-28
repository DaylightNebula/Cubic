package daylightnebula.cubic.fabric.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.ObjectSelectionList
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.network.chat.Component

// setup some interfaces
interface WidgetElement: GuiEventListener, Renderable, NarratableEntry
interface WidgetUser { fun <T: WidgetElement> addWidget(widget: T): T }

// create list functions
abstract class ListEntry: AutoCloseable, ObjectSelectionList.Entry<ListEntry>()
fun WidgetUser.list(
    minecraft: Minecraft,
    entries: List<ListEntry>,
    width: Int, height: Int,
    top: Int, bottom: Int,
    xOffset: Int? = null,
    yOffset: Int? = null
) {
    val element = this.addWidget(object: ObjectSelectionList<ListEntry>(minecraft, width, height, top, bottom), WidgetElement {
        init {
            entries.forEach(this::addEntry)
        }
    })

    xOffset?.let { element.x = it }
    yOffset?.let { element.y = it }
}

// create list function with a render callback
fun <D: Any> WidgetUser.renderList(
    minecraft: Minecraft,
    datas: List<D>,
    width: Int, height: Int,
    top: Int, bottom: Int,
    xOffset: Int? = null,
    yOffset: Int? = null,
    callback: (context: RenderContext, data: D) -> Unit
) {
    val element = this.addWidget(object: ObjectSelectionList<ListEntry>(minecraft, width, height, top, bottom), WidgetElement {
        val entries = datas.map { ListCallbackEntry(minecraft, it, callback) }

        init {
            entries.forEach(this::addEntry)
        }

        override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
            entries.forEach { it.clicked = true }
            return super<ObjectSelectionList>.mouseClicked(d, e, i)
        }
    })

    xOffset?.let { element.x = it }
    yOffset?.let { element.y = it }
}

// ListEntry that contains data, minecraft instance and callback for rendering
class ListCallbackEntry<D: Any>(
    private val minecraft: Minecraft,
    private val data: D,
    val callback: (context: RenderContext, data: D) -> Unit
): ListEntry() {
    var clicked = false

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
        callback(RenderContext(minecraft, gui, x, y, width, height, mouseX, mouseY, hovered, clicked, tickDelta), data)
        clicked = false
    }

    override fun getNarration(): Component = Component.literal("List callback entry")
    override fun close() {}
}
