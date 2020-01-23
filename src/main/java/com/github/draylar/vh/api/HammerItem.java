package com.github.draylar.vh.api;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.registry.Enchantments;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        float modifier = EnchantmentHelper.getLevel(Enchantments.CURSE_OF_GIGANTISM, stack) == 1 ? .2f : 1f;
        return super.getMiningSpeed(stack, state) * modifier;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (VanillaHammers.CONFIG.breakSingleBlockWhenSneaking && player.isSneaking()) {
            return true;
        }

        float originHardness = world.getBlockState(blockPos).getHardness(null, null);
        boolean gigantism = EnchantmentHelper.getEquipmentLevel(Enchantments.CURSE_OF_GIGANTISM, player) == 1;

        // only do a 3x3 break if the player's tool is effective on the block they are breaking
        // this makes it so breaking gravel doesn't break nearby stone
        if (player.getMainHandStack().isEffectiveOn(world.getBlockState(blockPos))) {
            BlockBreaker.breakInRadius(world, player, gigantism ? 2 : 1, (breakState) -> {
                double hardness = breakState.getHardness(null, null);
                boolean isEffective = player.getMainHandStack().isEffectiveOn(breakState);
                boolean verifyHardness = hardness < originHardness * 5 && hardness > 0;
                return isEffective && verifyHardness;
            }, true);
        }

        return true;
    }
}
