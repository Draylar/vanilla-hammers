package com.github.draylar.vh.registry;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.config.VanillaHammersConfig;
import com.github.draylar.vh.api.HammerItem;
import com.github.draylar.vh.material.HammerMaterials;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {

    public static Item WOOD = register(HammerMaterials.WOOD, 3, -2.5f, "wooden");
    public static Item STONE = register(HammerMaterials.STONE, 4, -2.6f, "stone");
    public static Item IRON = register(HammerMaterials.IRON, 5, -2.8f, "iron");
    public static Item GOLDEN = register(HammerMaterials.GOLD, 5, -2.5f, "golden");
    public static Item DIAMOND = register(HammerMaterials.DIAMOND, 10, -3f, "diamond");
    public static Item NETHERITE = register(HammerMaterials.NETHERITE, 11, -3.1f, "netherite");

    public static Item EMERALD;
    public static Item OBSIDIAN;
    public static Item LAPIS;
    public static Item QUARTZ;
    public static Item FIERY;
    public static Item PRISMARINE;
    public static Item ENDER;
    public static Item SLIME;

    public static Item TATER;

    public static Item GLOWSTONE;
    public static Item NETHER;
    public static Item VIBRANIUM;

    public static void init() {
        if (AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableExtraMaterials) {
            EMERALD = register(HammerMaterials.EMERALD, 11, -3f, "emerald");
            OBSIDIAN = register(HammerMaterials.OBSIDIAN, 7, -3.5f, "obsidian");
            LAPIS = register(HammerMaterials.QUARTZ, 5, -2f, "lapis");
            QUARTZ = register(HammerMaterials.LAPIS, 3, -2.5f, "quartz");
            FIERY = register(HammerMaterials.FIERY, 6, -2.3f, "fiery");
            PRISMARINE = register(HammerMaterials.PRISMARINE, 6, -2.3f, "prismarine");
            ENDER = register(HammerMaterials.ENDER, 10, -3.3f, "ender");
            SLIME = register(HammerMaterials.SLIME, 7, -3f, "slime");
        }

        if (AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableTaterHammer) {
            if (FabricLoader.getInstance().isModLoaded("lil-tater")) {
                TATER = register(HammerMaterials.POTATO, 3, -2.3f, "tater");
            }
        }

        if (FabricLoader.getInstance().isModLoaded("netherthings")) {
            GLOWSTONE = register(HammerMaterials.GLOWSTONE, 5, -2.3f, "glowstone");
            NETHER = register(HammerMaterials.NETHER, 4, -2.1f, "nether");
            VIBRANIUM = register(HammerMaterials.VIBRANIUM, 10, -2.9f, "vibranium");
        }
    }

    private static Item register(ToolMaterial material, int attackDamage, float attackSpeed, String hammerName) {
        return Registry.register(Registry.ITEM, VanillaHammers.id(hammerName + "_hammer"), new HammerItem(material, attackDamage, attackSpeed));
    }
}
