package draylar.vh;

import dev.draylar.magna.api.optional.MagnaOptionals;
import draylar.omegaconfig.OmegaConfig;
import draylar.staticcontent.StaticContent;
import draylar.vh.config.VanillaHammersConfig;
import draylar.vh.data.HammerData;
import draylar.vh.item.ExtendedHammerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class VanillaHammers implements ModInitializer {

	public static String MODID = "vanilla-hammers";
	public static VanillaHammersConfig CONFIG = OmegaConfig.register(VanillaHammersConfig.class);
	private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, id("group"));

	@Override
	public void onInitialize() {
		MagnaOptionals.optInForCurse();
		StaticContent.load(id("hammers"), HammerData.class);
		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.vanilla-hammers.group"))
				.icon(() -> new ItemStack(Registries.ITEM.get(id("diamond_hammer"))))
				.entries((context, entries) -> entries.addAll(HammerData.ENTRY_SET))
			.build()
		);
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