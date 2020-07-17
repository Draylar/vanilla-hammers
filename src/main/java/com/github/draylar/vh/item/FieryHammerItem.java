package com.github.draylar.vh.item;

import draylar.magna.api.BlockProcessor;
import draylar.magna.item.HammerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class FieryHammerItem extends HammerItem {

    public FieryHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public BlockProcessor getProcessor(World world, PlayerEntity player, BlockPos pos, ItemStack heldStack) {
        return (tool, input) -> {
            Optional<SmeltingRecipe> cooked = world.getRecipeManager().getFirstMatch(
                    RecipeType.SMELTING,
                    new SimpleInventory(input),
                    world
            );

            if (cooked.isPresent()) {
                return cooked.get().getOutput().copy();
            }

            return input;
        };
    }
}
