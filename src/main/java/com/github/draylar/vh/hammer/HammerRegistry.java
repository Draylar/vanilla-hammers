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
        register(HammerMaterials.WOOD, 3, -2, "wooden");
        register(HammerMaterials.STONE, 4, -2.1f, "stone");
        register(HammerMaterials.IRON, 5, -2.3f, "iron");
        register(HammerMaterials.GOLD, 5, -2, "golden");
        register(HammerMaterials.DIAMOND, 10, -2.5f, "diamond");

        if(ConfigHolder.configInstance.enableExtraMaterials)
        {
            register(HammerMaterials.EMERALD, 11, -2.5f, "emerald");
            register(HammerMaterials.OBSIDIAN, 7, -3f, "obsidian");
            register(HammerMaterials.QUARTZ, 5, -1.5f, "lapis");
            register(HammerMaterials.LAPIS, 3, -2, "quartz");
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
