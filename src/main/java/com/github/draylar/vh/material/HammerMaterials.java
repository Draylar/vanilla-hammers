package com.github.draylar.vh.material;

import com.github.draylar.vh.VanillaHammers;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public enum HammerMaterials implements ToolMaterial {
    NETHER(1, 280 * VanillaHammers.CONFIG.durabilityModifier, 5.0F / 3.5f, 0.0f, 77, () -> {
        return Ingredient.ofItems(Registry.ITEM.get(new Identifier("minecraft", "nether_brick")));
    }),
    POTATO(1, 500 * VanillaHammers.CONFIG.durabilityModifier, 4.0F / 3.5f, 0.0f, 100, () -> {
        return Ingredient.ofItems(Registry.ITEM.get(new Identifier("lil-tater", "lil_tater")));
    }),
    GLOWSTONE(2, 442 * VanillaHammers.CONFIG.durabilityModifier, 5.0F / 3.5f, 0.0f, 15, () -> {
        return Ingredient.ofItems(Registry.ITEM.get(new Identifier("netherthings", "glowstone_ingot")));
    }),
    VIBRANIUM(3, (int) (21850d * (VanillaHammers.CONFIG.durabilityModifier / 2.5f)), 22f / 3.5f, 0.0f, 7, () -> {
        return Ingredient.ofItems(
                Registry.ITEM.get(new Identifier("netherthings", "vibranium")),
                Registry.ITEM.get(new Identifier("adabraniummod","vibranium_ingot"))

        );
    });

    private final int miningLevel;
    private final int durability;
    private final float blockBreakSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    HammerMaterials(int miningLevel, int durability, float blockBreakSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.blockBreakSpeed = blockBreakSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<>(repairIngredient);
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.blockBreakSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
