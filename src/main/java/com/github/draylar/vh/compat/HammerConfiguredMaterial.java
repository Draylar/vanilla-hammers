package com.github.draylar.vh.compat;

import com.github.draylar.vh.VanillaHammers;
import io.github.cottonmc.dynagear.api.ConfiguredMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.sound.SoundEvent;

public class HammerConfiguredMaterial extends ConfiguredMaterial {
	public HammerConfiguredMaterial(ConfiguredMaterial mat) {
		this(mat.getName(), Integer.toString(mat.getColor()), mat.getMaterialId(), mat.getBlockMaterialId(), mat.asTool().getEnchantability(),
				mat.asTool().getDurability(), mat.asTool().getMiningLevel(), mat.asTool().getMiningSpeed(), mat.asTool().getAttackDamage(),
				mat.asArmor().getDurability(EquipmentSlot.FEET)/13, getProtAmounts(mat), mat.asArmor().getToughness(), mat.asArmor().getEquipSound());
	}

	public HammerConfiguredMaterial(String name, String color, String matId, String blockMatId, int enchantability,
									int toolDurability, int miningLevel, float miningSpeed, float attackDamage,
									int armorDurabilityMultiplier, int[] protectionAmounts, float toughness, SoundEvent equipSound)
	{
		super(name, color, matId, blockMatId, enchantability,
				toolDurability*VanillaHammers.DURABILITY_MODIFIER, miningLevel, miningSpeed, attackDamage,
				armorDurabilityMultiplier, protectionAmounts, toughness, equipSound);
	}

	static int[] getProtAmounts(ConfiguredMaterial mat) {
		ArmorMaterial armorMat = mat.asArmor();
		int[] ret = new int[4];
		ret[0] = armorMat.getProtectionAmount(EquipmentSlot.FEET);
		ret[1] = armorMat.getProtectionAmount(EquipmentSlot.LEGS);
		ret[2] = armorMat.getProtectionAmount(EquipmentSlot.CHEST);
		ret[3] = armorMat.getProtectionAmount(EquipmentSlot.HEAD);
		return ret;
	}
}
