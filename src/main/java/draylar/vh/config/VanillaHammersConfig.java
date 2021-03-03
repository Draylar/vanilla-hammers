package draylar.vh.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "vanilla-hammers")
public class VanillaHammersConfig implements ConfigData {
    public boolean enableExtraMaterials = true;
    public int durabilityModifier = 5;
    public double breakSpeedMultiplier = 1.0;
}