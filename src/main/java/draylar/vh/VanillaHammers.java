package draylar.vh;

import draylar.magna.api.optional.MagnaOptionals;
import draylar.omegaconfig.OmegaConfig;
import draylar.staticcontent.StaticContent;
import draylar.vh.config.VanillaHammersConfig;
import draylar.vh.data.HammerData;
import draylar.vh.item.ExtendedHammerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VanillaHammers implements ModInitializer {

	public static String MODID = "vanilla-hammers";
	public static VanillaHammersConfig CONFIG = OmegaConfig.register(VanillaHammersConfig.class);
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Registry.ITEM.get(id("diamond_hammer"))));

	@Override
	public void onInitialize() {
		MagnaOptionals.optInForCurse();
		StaticContent.load(id("hammers"), HammerData.class);
		registerCallbackHandlers();
	}

	private void registerCallbackHandlers() {
		AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			ItemStack handStack = playerEntity.getMainHandStack();
			if(handStack.getItem() instanceof ExtendedHammerItem extendedHammerItem) {
				// set entity on fire if this hammer smelts blocks
				if(extendedHammerItem.getData().canSmelt()) {
					entity.setOnFireFor(4);
				}
			}

			return ActionResult.PASS;
		});
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}