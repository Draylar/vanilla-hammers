package com.github.draylar.vh.compat;

import com.github.draylar.vh.hammer.HammerItem;
import io.github.cottonmc.dynagear.api.ConfiguredMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DynaHammerItem extends HammerItem
{
	private ConfiguredMaterial material;
	public DynaHammerItem(ConfiguredMaterial material, Item.Settings settings)
	{
		super(new HammerConfiguredMaterial(material).asTool(), (int)material.asTool().getAttackDamage(), -2.75f, settings);
		this.material = material;
	}

	@Override
	public String getTranslationKey()
	{
		return "item.vanilla-hammers.dynagear_hammer";
	}

	@Override
	public Text getName()
	{
		String mat = material.getName().substring(0, 1).toUpperCase() + material.getName().substring(1);
		return new TranslatableText(getTranslationKey(), mat);
	}

	@Override
	public Text getName(ItemStack stack)
	{
		String mat = material.getName().substring(0, 1).toUpperCase() + material.getName().substring(1);
		return new TranslatableText(getTranslationKey(), mat);
	}
}
