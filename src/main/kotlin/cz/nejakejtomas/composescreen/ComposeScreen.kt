package cz.nejakejtomas.composescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component

class ComposeScreen(
    title: Component,
    private val parent: Screen? = null,
    private val content: @Composable (ui: ComposeUi) -> Unit,
) : Screen(title) {

    private lateinit var mc: Minecraft
    private lateinit var ui: ComposeUi
    private val renderDispatcher = object : LoopDispatcher() {
        fun runPublic() = run()
    }

    override fun init() {
        super.init()

        if (!this::mc.isInitialized) mc = minecraft!!
        if (!this::ui.isInitialized) {
            ui = ComposeUi(
                mc,
                renderDispatcher,
                (width * scale).toInt(),
                (height * scale).toInt(),
                scale,
                content,
            )
        }
    }

    override fun resize(minecraft: Minecraft, width: Int, height: Int) {
        super.resize(minecraft, width, height)
        ui.setSizeAndDensity(width, height, Density(scale))
    }

    private val scale: Float
        get() = mc.window.guiScale.toFloat()

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(guiGraphics, i, j, f)
        renderDispatcher.runPublic()

        guiGraphics.blit(
            RenderType::guiTextured,
            ui.textureResource,
            0,
            0,
            0f,
            0f,
            width,
            height,
            width,
            height
        )
    }

    override fun onClose() {
        ui.close()
        mc.setScreen(parent)
    }

    override fun mouseClicked(x: Double, y: Double, i: Int): Boolean {
        ui.mouseDown(Offset(x.toFloat(), y.toFloat()) * scale, i)
        return super.mouseClicked(x, y, i)
    }

    override fun mouseReleased(x: Double, y: Double, i: Int): Boolean {
        ui.mouseUp(Offset(x.toFloat(), y.toFloat()) * scale, i)
        return super.mouseReleased(x, y, i)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        ui.keyDown(keyCode, scanCode, modifiers)

        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        ui.keyUp(keyCode, scanCode, modifiers)

        return super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun mouseMoved(x: Double, y: Double) {
        ui.mouseMove(Offset(x.toFloat(), y.toFloat()) * scale)
        super.mouseMoved(x, y)
    }

    override fun mouseScrolled(x: Double, y: Double, scrollX: Double, scrollY: Double): Boolean {
        ui.scroll(Offset(x.toFloat(), y.toFloat()) * scale, Offset(-scrollX.toFloat(), -scrollY.toFloat()))
        return super.mouseScrolled(x, y, scrollX, scrollY)
    }
}

