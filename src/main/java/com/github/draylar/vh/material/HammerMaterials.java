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
    WOOD(0, 59 * VanillaHammers.CONFIG.durabilityModifier, 2.0F / 3.5f, 0.0f, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }),
    STONE(1, 131 * VanillaHammers.CONFIG.durabilityModifier, 4.0F / 3.5f, 0.0f, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }),
    IRON(2, 250 * VanillaHammers.CONFIG.durabilityModifier, 6.0F / 3.5f, 0.0f, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }),
    DIAMOND(3, 1561 * VanillaHammers.CONFIG.durabilityModifier, 8.0F / 3.5f, 0.0f, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }),
    GOLD(0, 32 * VanillaHammers.CONFIG.durabilityModifier, 12.0F / 3.5f, 0.0f, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }),
    EMERALD(3, 1028 * VanillaHammers.CONFIG.durabilityModifier, 12.0F / 3.5f, 0.0f, 25, () -> {
        return Ingredient.ofItems(Items.EMERALD);
    }),
    OBSIDIAN(2, 2048 * VanillaHammers.CONFIG.durabilityModifier, 5.0F / 3.5f, 0.0f, 5, () -> {
        return Ingredient.ofItems(Items.OBSIDIAN);
    }),
    LAPIS(1, 220 * VanillaHammers.CONFIG.durabilityModifier, 6.0f / 3.5f, 0.0f, 30, () -> {
        return Ingredient.ofItems(Items.LAPIS_LAZULI);
    }),
    QUARTZ(2, 1028 * VanillaHammers.CONFIG.durabilityModifier, 8.0F / 3.5f, 0.0f, 10, () -> {
        return Ingredient.ofItems(Items.QUARTZ);
    }),
    FIERY(3, 750 * VanillaHammers.CONFIG.durabilityModifier, 7.0F / 3.5f, 0f, 15, () -> {
        return Ingredient.ofItems(Items.MAGMA_BLOCK);
    }),
    PRISMARINE(3, 750 * VanillaHammers.CONFIG.durabilityModifier, 7.0F / 3.5F, 0f, 20, () -> {
        return Ingredient.ofItems(Items.PRISMARINE_SHARD);
    }),
    ENDER(3, 1561 * VanillaHammers.CONFIG.durabilityModifier, 10f / 3.5f, 0, 10, () -> {
        return Ingredient.ofItems(Items.ENDER_PEARL);
    }),
    SLIME(2, 1500 * VanillaHammers.CONFIG.durabilityModifier, 6f / 3.5f, 0, 20, () -> {
        return Ingredient.ofItems(Items.SLIME_BALL);
    }),
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
                Registry.ITEM.get(new Identifier("adabraniummod","vibranium"))
        );
    });


    private final int miningLevel;
    private final int durability;
    private final float blockBreakSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    HammerMaterials(int int_1, int int_2, float float_1, float float_2, int int_3, Supplier<Ingredient> supplier_1) {
        this.miningLevel = int_1;
        this.durability = int_2;
        this.blockBreakSpeed = float_1;
        this.attackDamage = float_2;
        this.enchantability = int_3;
        this.repairIngredient = new Lazy(supplier_1);
    }

    public int getDurability() {
        return this.durability;
    }

    public float getMiningSpeed() {
        return this.blockBreakSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
