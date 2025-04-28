package cz.nejakejtomas.mccomposerender

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import com.mojang.blaze3d.platform.NativeImage
import org.jetbrains.skia.*

internal class NativeImageWithCanvas private constructor(width: Int, height: Int, private val bitmap: ImageBitmap) :
    NativeImage(Format.RGBA, width, height, true, bitmap.asSkiaBitmap().peekPixels()!!.addr) {

    val canvas = Canvas(bitmap)

    override fun close() {
        this.pixels = 0L
        super.close()
    }

    fun clear() {
        bitmap.asSkiaBitmap().erase(Color.TRANSPARENT)
    }

    companion object {
        private val skiaBackedImageBitmapConstructor: java.lang.reflect.Constructor<*> = run {
            val clazz = Class.forName("androidx.compose.ui.graphics.SkiaBackedImageBitmap")
            val skiaBackedImageBitmap = clazz.declaredConstructors.single()
            skiaBackedImageBitmap.isAccessible = true
            skiaBackedImageBitmap
        }

        private fun create(bitmap: Bitmap): ImageBitmap {
            return skiaBackedImageBitmapConstructor.newInstance(bitmap) as ImageBitmap
        }

        operator fun invoke(width: Int, height: Int): NativeImageWithCanvas {
            val colorInfo = ColorInfo(ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.sRGB)
            val imageInfo = ImageInfo(colorInfo, width, height)

            val bitmap = Bitmap()
            bitmap.allocPixels(imageInfo)

            val skiaBackedBitmap = create(bitmap)

            return NativeImageWithCanvas(width, height, skiaBackedBitmap)
        }
    }
}