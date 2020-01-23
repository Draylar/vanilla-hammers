package com.github.draylar.vh.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "vanilla-hammers")
public class VanillaHammersConfig implements ConfigData
{
    public boolean enableExtraMaterials = true;
    public boolean enableTaterHammer = true;
    public boolean enableFull3x3 = false;
    public boolean fullBlockHitbox = false;
    public boolean alwaysShowSingleBlockHitbox = false;
    public boolean showSingleBlockWhenSneaking = true;
    public boolean breakSingleBlockWhenSneaking = true;
}
