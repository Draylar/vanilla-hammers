package com.github.draylar.vh.data;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.mixin.ItemGroupAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.beans.Transient;

public class HammerData {

    private final String id;
    private final int miningLevel;
    private final int durability;
    private final float blockBreakSpeed;
    private final float attackDamage;
    private final float attackSpeed;
    private final int enchantability;
    private final Identifier repairIngredient;
    private final boolean isFireImmune;
    private final boolean smelts;
    private final int breakRadius;
    private final boolean isExtra;
    private final int burnTime;
    private final boolean hasExtraKnockback;
    private final String group;
    private transient ItemGroup cachedGroup;

    public HammerData(String id, int miningLevel, int durability, float blockBreakSpeed, float attackDamage, float attackSpeed, int enchantability, Identifier repairIngredient, boolean isFireImmune, boolean smelts, int breakRadius, boolean isExtra, int burnTime, boolean hasExtraKnockback, String group) {
        this.id = id;
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.blockBreakSpeed = blockBreakSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.isFireImmune = isFireImmune;
        this.smelts = smelts;
        this.breakRadius = breakRadius;
        this.isExtra = isExtra;
        this.burnTime = burnTime;
        this.hasExtraKnockback = hasExtraKnockback;
        this.group = group;
    }

    public ItemGroup getGroup() {
        // attempt to get group from string
        if(cachedGroup == null && (group != null && !group.isEmpty())) {
            for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                if(((ItemGroupAccessor) itemGroup).getId().equals(group)) {
                    cachedGroup = itemGroup;
                    break;
                }
            }
        }

        return cachedGroup;
    }

    public boolean hasExtraKnockback() {
        return hasExtraKnockback;
    }

    public boolean canSmelt() {
        return smelts;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public String getId() {
        return id;
    }

    public int getBreakRadius() {
        return breakRadius;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getDurability() {
        return durability;
    }

    public float getBlockBreakSpeed() {
        return blockBreakSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public Identifier getRepairIngredient() {
        return repairIngredient;
    }

    public boolean isFireImmune() {
        return isFireImmune;
    }
}
