package com.github.draylar.vh.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class CurseOfGigantismEnchantment extends Enchantment {

    public CurseOfGigantismEnchantment() {
        super(Enchantment.Weight.VERY_RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    protected boolean differs(Enchantment other) {
        return other == Enchantments.EFFICIENCY;
    }

    @Override
    public int getMinimumPower(int level) {
        return 25;
    }

    @Override
    public int getMaximumPower(int level) {
        return 50;
    }

    @Override
    public int getMaximumLevel() {
        return 1;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
