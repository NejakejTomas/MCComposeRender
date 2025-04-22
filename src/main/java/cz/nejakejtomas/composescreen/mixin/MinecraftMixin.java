package cz.nejakejtomas.composescreen.mixin;

import cz.nejakejtomas.composescreen.MainDispatcher;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    // Render
    @Inject(method = "runTick", at = @At("HEAD"))
    void tick(boolean bl, CallbackInfo ci) {
        MainDispatcher.INSTANCE.run$ComposeScreen();
    }
}
