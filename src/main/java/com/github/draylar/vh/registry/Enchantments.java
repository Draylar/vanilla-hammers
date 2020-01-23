package com.github.draylar.vh.registry;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.enchantment.CurseOfGigantismEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class Enchantments {

    public static Enchantment CURSE_OF_GIGANTISM = Registry.register(
            Registry.ENCHANTMENT,
            VanillaHammers.id("gigantism_curse"),
            new CurseOfGigantismEnchantment()
    );

    public static void init() {
        // NO-OP
    }

    private Enchantments() {
        // NO-OP
    }
}
