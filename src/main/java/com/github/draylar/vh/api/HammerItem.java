package com.github.draylar.vh.api;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.config.VanillaHammersConfig;
import com.google.common.collect.ImmutableSet;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

import java.util.Set;

public class HammerItem extends PickaxeItem
{
    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed)
    {
        this(toolMaterial, attackDamage, attackSpeed, new Item.Settings().group(ItemGroup.TOOLS));
    }

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings)
    {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEffectiveOn(BlockState state) {
        return Items.DIAMOND_PICKAXE.isEffectiveOn(state);
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
                boolean effective = player.getMainHandStack().isEffectiveOn(breakState);
                boolean hardness = breakState.getHardness(null, null) < originHardness * 5;
                return effective && hardness;
            }, true);
        }


        return true;
    }
}

