package cz.nejakejtomas.composescreen

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

val LocalScreen: ProvidableCompositionLocal<Screen?> = staticCompositionLocalOf { Minecraft.getInstance().screen }
val LocalMinecraft: ProvidableCompositionLocal<Minecraft> = staticCompositionLocalOf { Minecraft.getInstance() }