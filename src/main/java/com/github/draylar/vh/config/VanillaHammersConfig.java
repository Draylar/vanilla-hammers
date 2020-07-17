package com.github.draylar.vh.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "vanilla-hammers")
public class VanillaHammersConfig implements ConfigData {
    public boolean enableExtraMaterials = true;
    public boolean enableTaterHammer = true;
    public int durabilityModifier = 5;
}