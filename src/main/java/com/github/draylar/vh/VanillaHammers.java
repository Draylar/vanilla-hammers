package com.github.draylar.vh;

import com.github.draylar.vh.config.VanillaHammersConfig;
import com.github.draylar.vh.common.HammerRegistry;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;

public class VanillaHammers implements ModInitializer
{
	public static final int DURABILITY_MODIFIER = 5;
	public static VanillaHammersConfig CONFIG = AutoConfig.register(VanillaHammersConfig.class, GsonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize()
	{
		HammerRegistry.registerHammers();
		FuelRegistryImpl.INSTANCE.add(HammerRegistry.WOOD, 400);
	}
}