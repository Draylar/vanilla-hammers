package draylar.vh.mixin;

import draylar.vh.item.ExtendedHammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SilkTouchEnchantment.class)
public class SilkTouchEnchantmentMixin extends Enchantment {

    public SilkTouchEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (stack.getItem() instanceof ExtendedHammerItem item) {
            return !item.getData().canSmelt();
        }

        return super.isAcceptableItem(stack);
    }
}
