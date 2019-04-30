package com.github.draylar.vh;

import com.github.draylar.vh.config.ConfigLoader;
import com.github.draylar.vh.hammer.HammerItem;
import com.github.draylar.vh.hammer.HammerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.registry.FuelRegistryImpl;

public class VanillaHammers implements ModInitializer
{
	public static final int DURABILITY_MODIFIER = 5;

	@Override
	public void onInitialize()
	{
		new ConfigLoader().checkConfigFolder();
		HammerRegistry.registerHammers();
		FuelRegistryImpl.INSTANCE.add(HammerRegistry.WOOD, 400);
	}
}