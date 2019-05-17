package com.github.draylar.vh.hammer;

import com.github.draylar.vh.config.VanillaHammersConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HammerRegistry
{
    public static HammerItem WOOD;

    public static void registerHammers()
    {
        WOOD = register(HammerMaterials.WOOD, 3, -2.5f, "wooden");
        register(HammerMaterials.STONE, 4, -2.6f, "stone");
        register(HammerMaterials.IRON, 5, -2.8f, "iron");
        register(HammerMaterials.GOLD, 5, -2.5f, "golden");
        register(HammerMaterials.DIAMOND, 10, -3f, "diamond");

        if(AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableExtraMaterials)
        {
            register(HammerMaterials.EMERALD, 11, -3f, "emerald");
            register(HammerMaterials.OBSIDIAN, 7, -3.5f, "obsidian");
            register(HammerMaterials.QUARTZ, 5, -2f, "lapis");
            register(HammerMaterials.LAPIS, 3, -2.5f, "quartz");
            register(HammerMaterials.FIERY, 6, -2.3f, "fiery");
            register(HammerMaterials.PRISMARINE, 6, -2.3f, "prismarine");
            register(HammerMaterials.ENDER, 10, -3.3f, "ender");
            register(HammerMaterials.SLIME, 7, -3f, "slime");
        }


        if(AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableTaterHammer)
        {
            if (FabricLoader.getInstance().isModLoaded("lil-tater"))
            {
                register(HammerMaterials.POTATO, 3, -2.3f, "tater");
            }
        }
    }

    private static HammerItem register(ToolMaterial material, int attackDamage, float attackSpeed, String hammerName)
    {
        return Registry.register(Registry.ITEM, new Identifier("vanilla-hammers", hammerName + "_hammer"), new HammerItem(material, attackDamage, attackSpeed));
    }
}
