package com.github.draylar.vh;

import com.github.draylar.vh.config.VanillaHammersConfig;
import com.github.draylar.vh.common.HammerRegistry;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class VanillaHammers implements ModInitializer
{
	public static final int DURABILITY_MODIFIER = 5;
	public static VanillaHammersConfig CONFIG = AutoConfig.register(VanillaHammersConfig.class, GsonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize()
	{
		HammerRegistry.registerHammers();
		FuelRegistry.INSTANCE.add(HammerRegistry.WOOD, 400);
	}
}