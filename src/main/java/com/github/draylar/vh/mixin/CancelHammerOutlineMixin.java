package com.github.draylar.vh.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class CancelHammerOutlineMixin
{
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "drawHighlightedBlockOutline", cancellable = true)
    public void draw(Camera camera_1, HitResult hitResult_1, int int_1, CallbackInfo ci)
    {
        if(this.client.player.inventory.getMainHandStack().getItem().getTranslationKey().contains("hammer"))
        {
            ci.cancel();
        }
    }
}
