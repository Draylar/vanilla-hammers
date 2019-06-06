package com.github.draylar.vh.mixin;

import net.minecraft.recipe.RecipeManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipeManager.class)
public class MixinCensorRecipeError
{
    @Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void censorError(final Logger logger, final String message, final Object p0, final Object p1)
    {
        if(message.contains("vanilla-hammers")) return;
    }
}
