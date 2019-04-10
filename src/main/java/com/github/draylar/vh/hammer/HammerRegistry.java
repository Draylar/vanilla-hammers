package com.github.draylar.vh.hammer;

import com.github.draylar.vh.config.ConfigHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.resource.ResourceReloadHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HammerRegistry
{
    public static void registerHammers()
    {
        register(HammerMaterials.WOOD, 3, -2.5f, "wooden");
        register(HammerMaterials.STONE, 4, -2.6f, "stone");
        register(HammerMaterials.IRON, 5, -2.8f, "iron");
        register(HammerMaterials.GOLD, 5, -2.5f, "golden");
        register(HammerMaterials.DIAMOND, 10, -3f, "diamond");

        if(ConfigHolder.configInstance.enableExtraMaterials)
        {
            register(HammerMaterials.EMERALD, 11, -3f, "emerald");
            register(HammerMaterials.OBSIDIAN, 7, -3.5f, "obsidian");
            register(HammerMaterials.QUARTZ, 5, -2f, "lapis");
            register(HammerMaterials.LAPIS, 3, -2.5f, "quartz");
        }


        if(ConfigHolder.configInstance.enableTaterHammer)
        {
            if (FabricLoader.getInstance().isModLoaded("lil-tater"))
            {
                register(HammerMaterials.POTATO, 3, 0, "tater");
            }
        }
    }

    private static void register(ToolMaterial material, int attackDamage, float attackSpeed, String hammerName)
    {
        Registry.register(Registry.ITEM, new Identifier("vanilla-hammers", hammerName + "_hammer"), new HammerItem(material, attackDamage, attackSpeed));
    }
}
