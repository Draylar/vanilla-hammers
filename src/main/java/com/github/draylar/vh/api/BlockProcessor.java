package com.github.draylar.vh.api;

import net.minecraft.item.ItemStack;

public interface BlockProcessor {
    ItemStack process(ItemStack tool, ItemStack input);
}
