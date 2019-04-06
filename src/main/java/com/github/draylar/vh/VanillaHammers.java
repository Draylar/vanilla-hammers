package com.github.draylar.vh;

import com.github.draylar.vh.config.ConfigLoader;
import com.github.draylar.vh.hammer.HammerRegistry;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VanillaHammers implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		new ConfigLoader().checkConfigFolder();
		HammerRegistry.registerHammers();
	}
}
