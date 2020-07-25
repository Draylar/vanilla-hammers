package com.github.draylar.vh.material;

import com.github.draylar.vh.data.HammerData;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomToolMaterial implements ToolMaterial {


    private int enchantability;
    private float miningSpeedMultiplier;
    private int durability;
    private float attackDamage;
    private int miningLevel;
    private Ingredient ingredient;

    public CustomToolMaterial(int enchantability, float miningSpeedMultiplier, int durability, float attackDamage, int miningLevel, Ingredient ingredient) {
        this.enchantability = enchantability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.ingredient = ingredient;
    }

    public static CustomToolMaterial from(HammerData data) {
        return new CustomToolMaterial(
                data.getEnchantability() == 0 ? 15 : data.getEnchantability(),
                data.getBlockBreakSpeed() == 0 ? 1 : data.getBlockBreakSpeed(),
                data.getDurability() == 0 ? 500 : data.getDurability(),
                data.getAttackDamage() == 0 ? 4 : data.getAttackDamage(),
                data.getMiningLevel(),
                Ingredient.ofItems(Registry.ITEM.get(data.getRepairIngredient() == null ? new Identifier("iron_ingot") : data.getRepairIngredient()))
        );
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return ingredient;
    }
}
