package com.github.draylar.vh;

import com.github.draylar.vh.config.VanillaHammersConfig;
import com.github.draylar.vh.hammer.HammerRegistry;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.registry.FuelRegistryImpl;

public class VanillaHammers implements ModInitializer
{
	public static final int DURABILITY_MODIFIER = 5;

	@Override
	public void onInitialize()
	{
		AutoConfig.register(VanillaHammersConfig.class, GsonConfigSerializer::new);
		HammerRegistry.registerHammers();
		FuelRegistryImpl.INSTANCE.add(HammerRegistry.WOOD, 400);
	}
}