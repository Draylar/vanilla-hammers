package com.github.draylar.vh;

import com.github.draylar.vh.config.VanillaHammersConfig;
import com.github.draylar.vh.data.HammerData;
import com.github.draylar.vh.data.IdentifierDeserializer;
import com.github.draylar.vh.item.ExtendedHammerItem;
import com.github.draylar.vh.material.CustomToolMaterial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import draylar.magna.api.optional.MagnaOptionals;
import io.github.cottonmc.staticdata.StaticData;
import io.github.cottonmc.staticdata.StaticDataItem;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class VanillaHammers implements ModInitializer {

	public static String MODID = "vanilla-hammers";
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Identifier.class, new IdentifierDeserializer()).create();
	public static VanillaHammersConfig CONFIG = AutoConfig.register(VanillaHammersConfig.class, GsonConfigSerializer::new).getConfig();
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Registry.ITEM.get(id("diamond_hammer"))));

	@Override
	public void onInitialize() {
		MagnaOptionals.optInForCurse();
		loadHammerData();
		registerCallbackHandlers();
	}

	private void registerCallbackHandlers() {
		AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			ItemStack handStack = playerEntity.getMainHandStack();
			if(handStack.getItem() instanceof ExtendedHammerItem) {
				ExtendedHammerItem extendedHammerItem = (ExtendedHammerItem) handStack.getItem();

				// set entity on fire if this hammer smelts blocks
				if(extendedHammerItem.getData().canSmelt()) {
					entity.setOnFireFor(4);
				}
			}

			return ActionResult.PASS;
		});
	}

	private void loadHammerData() {
		Set<StaticDataItem> hammerFile = StaticData.getAllInDirectory("hammers");

		for(StaticDataItem item : hammerFile) {
			try {
				InputStreamReader reader = new InputStreamReader(item.createInputStream(), StandardCharsets.UTF_8);

				// read & register each hammer
				HammerData data = GSON.fromJson(reader, HammerData.class);
				register(data);

				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void register(HammerData data) {
		// only register if the hammer is not extra (wood -> diamond), or if it is extra (lapis, emerald, ...) and the option is enabled
		if (!data.isExtra() || data.isExtra() && CONFIG.enableExtraMaterials) {

			// setup settings
			Item.Settings settings = new Item.Settings().group(VanillaHammers.GROUP);
			if (data.isFireImmune()) {
				settings.fireproof();
			}

			// check for hammer autosmelt
			// create hammer with settings
			ExtendedHammerItem hammerItem = new ExtendedHammerItem(
					CustomToolMaterial.from(data),
					0,
					data.getAttackSpeed() == 0 ? -2.4f : data.getAttackSpeed(),
					settings,
					data.getBreakRadius() == 0 ? 1 : data.getBreakRadius(),
					data
			);

			// burn time for furnace fuel
			if (data.getBurnTime() > 0) {
				FuelRegistry.INSTANCE.add(hammerItem, data.getBurnTime());
			}

			// add hammer to tag
			String path = data.getId() + "_hammer";
			Identifier registryID = path.contains(":") ? new Identifier(path) : id(path);
			Registry.register(Registry.ITEM, registryID, hammerItem);
		}

	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}