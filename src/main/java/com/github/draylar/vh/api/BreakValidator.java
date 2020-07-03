package com.github.draylar.vh.api;

import net.minecraft.block.BlockState;

/**
 * Provides information on whether a {@link BlockState} can be broken.
 */
public interface BreakValidator {

    /**
     * Implementers should return whether the given {@link BlockState} can be broken.
     *
     * @param state  {@link BlockState} to check for break validity
     * @return  whether the given {@link BlockState} can be broken
     */
    boolean canBreak(BlockState state);
}