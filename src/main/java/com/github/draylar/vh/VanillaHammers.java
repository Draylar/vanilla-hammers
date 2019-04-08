package com.github.draylar.vh;

import com.github.draylar.vh.config.ConfigLoader;
import com.github.draylar.vh.hammer.HammerRegistry;
import net.fabricmc.api.ModInitializer;

public class VanillaHammers implements ModInitializer
{
	public static final int DURABILITY_MODIFIER = 3;

	@Override
	public void onInitialize()
	{
		new ConfigLoader().checkConfigFolder();
		HammerRegistry.registerHammers();
	}
}
