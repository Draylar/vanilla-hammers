package com.github.draylar.vh.api;

import net.minecraft.block.BlockState;

public interface BreakValidator {
    boolean canBreak(BlockState state);
}