package com.github.draylar.vh.mixin;

import com.github.draylar.vh.registry.Items;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class HammerKnockbackMixin {

    private static final int SLIME_KNOCKBACK_MODIFIER = 3;

    @Inject(at = @At("HEAD"), method = "getKnockback", cancellable = true)
    private static void getKnockback(LivingEntity livingEntity, CallbackInfoReturnable<Integer> info) {
        if (livingEntity.getMainHandStack().getItem().equals(Items.SLIME)) {
            info.setReturnValue(SLIME_KNOCKBACK_MODIFIER);
            info.cancel();
        }
    }
}