package com.github.draylar.vh.api;

import com.github.draylar.vh.VanillaHammers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HammerItem extends PickaxeItem {

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        this(toolMaterial, attackDamage, attackSpeed, new Item.Settings().group(ItemGroup.TOOLS));
    }

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (VanillaHammers.CONFIG.breakSingleBlockWhenSneaking && player.isSneaking()) {
            return true;
        }

        float originHardness = world.getBlockState(blockPos).getHardness(null, null);

        // only do a 3x3 break if the player's tool is effective on the block they are breaking
        // this makes it so breaking gravel doesn't break nearby stone
        if (player.getMainHandStack().isEffectiveOn(world.getBlockState(blockPos))) {
            BlockBreaker.breakInRadius(world, player, 1, (breakState) -> {
                double hardness = breakState.getHardness(null, null);
                boolean isEffective = player.getMainHandStack().isEffectiveOn(breakState);
                boolean verifyHardness = hardness < originHardness * 5 && hardness > 0;
                return isEffective && verifyHardness;
            }, true);
        }

        return true;
    }
}
