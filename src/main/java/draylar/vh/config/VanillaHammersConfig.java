package draylar.vh.config;

import dev.draylar.omegaconfig.api.Config;

public class VanillaHammersConfig implements Config {

    public boolean enableExtraMaterials = true;
    public int durabilityModifier = 5;
    public double breakSpeedMultiplier = 1.0;

    @Override
    public String getName() {
        return "vanilla-hammers";
    }
}