package com.github.draylar.vh.hammer;

import com.github.draylar.vh.config.ConfigHolder;
import net.fabricmc.loader.FabricLoader;
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
            if (FabricLoader.INSTANCE.isModLoaded("lil-tater"))
            {
                System.out.println("[Vanilla Hammers] lil tater is lil loaded, adding lil tater hammer");

                ToolMaterial toolMaterial = new ToolMaterial()
                {
                    @Override
                    public int getDurability()
                    {
                        return 500;
                    }

                    @Override
                    public float getBlockBreakingSpeed()
                    {
                        return 1.5f;
                    }

                    @Override
                    public float getAttackDamage()
                    {
                        return 4;
                    }

                    @Override
                    public int getMiningLevel()
                    {
                        return 1;
                    }

                    @Override
                    public int getEnchantability()
                    {
                        return 100;
                    }

                    @Override
                    public Ingredient getRepairIngredient()
                    {
                        return Ingredient.ofItems(Registry.ITEM.get(new Identifier("lil-tater", "lil_tater")));
                    }
                };

                register(toolMaterial, 3, 0, "tater");
            }
        }
    }

    private static void register(ToolMaterial material, int attackDamage, float attackSpeed, String hammerName)
    {
        Registry.register(Registry.ITEM, new Identifier("vanilla-hammers", hammerName + "_hammer"), new HammerItem(material, attackDamage, attackSpeed));
    }
}
