package com.github.draylar.vh.compat;

import io.github.cottonmc.dynagear.DynaGear;
import io.github.cottonmc.dynagear.api.EquipmentCategory;
import io.github.cottonmc.dynagear.api.EquipmentTypeAdder;
import io.github.cottonmc.dynagear.impl.EquipmentManager;
import io.github.cottonmc.dynagear.impl.SimpleEquipmentType;
import net.minecraft.item.Item;

public class DynaGearCompat implements EquipmentTypeAdder
{
	@Override
	public void addEquipmentTypes(EquipmentManager manager)
	{
		manager.addEquipmentType(new SimpleEquipmentType("hammer", new String[]{"%#%", " / ", " / "}, null, EquipmentCategory.TOOLS,
				(mat) -> new DynaHammerItem(mat, new Item.Settings().group(DynaGear.DYNAGEAR_GROUP))));
	}
}
