package cz.nejakejtomas.mccomposerender.providers

import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.NativeClipboard
import net.minecraft.client.KeyboardHandler
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

internal class Clipboard(private val keyboardHandler: KeyboardHandler) : androidx.compose.ui.platform.Clipboard {
    override val nativeClipboard: NativeClipboard
        get() = keyboardHandler::getClipboard to keyboardHandler::setClipboard

    override suspend fun getClipEntry(): ClipEntry {
        return ClipEntry(StringSelection(keyboardHandler.clipboard))
    }

    override suspend fun setClipEntry(clipEntry: ClipEntry?) {
        keyboardHandler.clipboard =
            (clipEntry?.nativeClipEntry as? Transferable)?.getTransferData(
                DataFlavor.stringFlavor
            ) as? String ?: ""
    }
}