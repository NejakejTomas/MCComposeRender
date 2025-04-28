package cz.nejakejtomas.mccomposerender.providers

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

val LocalScreen: ProvidableCompositionLocal<Screen?> = staticCompositionLocalOf { null }
val LocalMinecraft: ProvidableCompositionLocal<Minecraft> = staticCompositionLocalOf { Minecraft.getInstance() }