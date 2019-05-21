package com.github.draylar.vh.config;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;

@Config(name = "vanilla-hammers")
public class VanillaHammersConfig implements ConfigData
{
    public boolean enableExtraMaterials = true;
    public boolean enableTaterHammer = true;
    public boolean enableFull3x3 = false;
    public boolean fullBlockHitbox = false;
    public boolean alwaysShowSingleBlockHitbox = false;
    public boolean showSingleblockWhenSneaking = true;
    public boolean breakSingleBlockWhenSneaking = true;
}
