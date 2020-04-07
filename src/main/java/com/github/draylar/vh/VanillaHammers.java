package com.github.draylar.vh;

import com.github.draylar.vh.registry.Enchantments;
import com.github.draylar.vh.registry.Items;
import com.github.draylar.vh.config.VanillaHammersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class VanillaHammers implements ModInitializer {

	public static String MODID = "vanilla-hammers";
	public static VanillaHammersConfig CONFIG = AutoConfig.register(VanillaHammersConfig.class, GsonConfigSerializer::new).getConfig();
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Items.DIAMOND));

	@Override
	public void onInitialize() {
		Items.init();
		Enchantments.init();

		FuelRegistry.INSTANCE.add(Items.WOOD, 400);
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}