@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package cz.nejakejtomas.mccomposerender

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.scene.ComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.mojang.blaze3d.systems.RenderSystem
import cz.nejakejtomas.mccomposerender.providers.LocalMinecraft
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import org.jetbrains.skiko.FrameDispatcher

@OptIn(InternalComposeUiApi::class, ExperimentalFoundationApi::class)
class ComposeUi(
    private val minecraft: Minecraft,
    renderDispatcher: CoroutineDispatcher,
    private var pixelWidth: Int,
    private var pixelHeight: Int,
    scale: Float,
    private val content: @Composable (ui: ComposeUi) -> Unit,
) {
    private val uiScope = CoroutineScope(renderDispatcher)
    private val frameDispatcher = FrameDispatcher(uiScope) {
        renderFrame()
    }

    private var scene: ComposeScene = CanvasLayersComposeScene(
        coroutineContext = renderDispatcher,
        invalidate = { frameDispatcher.scheduleFrame() },
        size = IntSize(pixelWidth, pixelHeight),
        density = Density(scale / 2),
        layoutDirection = minecraft.languageManager.run { if (getLanguage(selected)?.bidirectional == true) LayoutDirection.Rtl else LayoutDirection.Ltr },
    ).apply {
        setContent {
            CompositionLocalProvider(
                LocalMinecraft provides minecraft,
            ) { content(this@ComposeUi) }
        }
    }

    private var image = NativeImageWithCanvas(pixelWidth, pixelHeight)
    private var texture = DynamicTexture({ "ComposeUi" }, image)
    val textureResource: ResourceLocation = uniqueResourceLocation()
    private var closed = false

    init {
        minecraft.textureManager.register(textureResource, texture)
    }

    fun close() {
        RenderSystem.assertOnRenderThread()
        if (closed) return
        closed = true
        scene.close()
        minecraft.textureManager.release(textureResource)
        uiScope.cancel()
    }

    fun setSizeAndDensity(width: Int, height: Int, density: Density) {
        RenderSystem.assertOnRenderThread()
        if (closed) return
        val newSize = IntSize((width * density.density).toInt(), (height * density.density).toInt())

        pixelWidth = newSize.width
        pixelHeight = newSize.height

        minecraft.textureManager.release(textureResource)
        image = NativeImageWithCanvas(pixelWidth, pixelHeight)
        texture = DynamicTexture({ "ComposeUi" }, image)
        minecraft.textureManager.register(textureResource, texture)

        scene.size = newSize
        scene.density = Density(density.density / 2)
    }

    fun keyDown(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendKeyEvent(Keys.getComposeKeyEvent(Keys.KeyEvent.Press, keyCode, scanCode, modifiers))
    }

    fun keyUp(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendKeyEvent(Keys.getComposeKeyEvent(Keys.KeyEvent.Release, keyCode, scanCode, modifiers))
    }

    fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendKeyEvent(Keys.getComposeTypeEvent(codePoint, modifiers))
    }

    fun mouseDown(offset: Offset, i: Int): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendPointerEvent(PointerEventType.Press, offset, button = PointerButton(i)).anyMovementConsumed
    }

    fun mouseUp(offset: Offset, i: Int): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendPointerEvent(PointerEventType.Release, offset, button = PointerButton(i)).anyMovementConsumed
    }

    fun scroll(offset: Offset, delta: Offset): Boolean {
        RenderSystem.assertOnRenderThread()
        if (closed) return false

        return scene.sendPointerEvent(PointerEventType.Scroll, offset, delta).anyMovementConsumed
    }

    fun mouseMove(offset: Offset) {
        RenderSystem.assertOnRenderThread()
        if (closed) return

        scene.sendPointerEvent(PointerEventType.Move, offset)
    }

    private fun renderFrame() {
        RenderSystem.assertOnRenderThread()
        image.clear()
        scene.render(image.canvas, System.nanoTime())
        texture.upload()
    }
}