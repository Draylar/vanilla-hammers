package com.github.draylar.vh.api;

import com.github.draylar.vh.VanillaHammers;
import com.github.draylar.vh.registry.Enchantments;
import com.github.draylar.vh.registry.Items;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * Represents a tool that can mine stone ({@link PickaxeItem}) in a certain radius.
 *
 * <p>If a {@link HammerItem} has the Curse of Gigantism, break radius is expanded by 1, and mining speed is reduced by %80.
 * Effectiveness is determined by this tool's {@link ToolMaterial}.
 */
public class HammerItem extends PickaxeItem {

    private int breakRadius = 1;

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, int breakRadius) {
        this(toolMaterial, attackDamage, attackSpeed, new Item.Settings().group(VanillaHammers.GROUP));
        this.breakRadius = breakRadius;
    }

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        this(toolMaterial, attackDamage, attackSpeed, new Item.Settings().group(VanillaHammers.GROUP));
    }

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings, int breakRadius) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.breakRadius = breakRadius;
    }

    public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        float modifier = EnchantmentHelper.getLevel(Enchantments.CURSE_OF_GIGANTISM, stack) == 1 ? .2f : 1f;
        return super.getMiningSpeedMultiplier(stack, state) * modifier;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (VanillaHammers.CONFIG.breakSingleBlockWhenSneaking && player.isSneaking()) {
            return true;
        }

        float originHardness = world.getBlockState(blockPos).getHardness(null, null);
        boolean hasCurseOfGigantism = EnchantmentHelper.getEquipmentLevel(Enchantments.CURSE_OF_GIGANTISM, player) == 1;

        // only do a 3x3 break if the player's tool is effective on the block they are breaking
        // this makes it so breaking gravel doesn't break nearby stone
        if (player.getMainHandStack().isEffectiveOn(world.getBlockState(blockPos))) {
            BlockBreaker.breakInRadius(
                    world,
                    player,
                    hasCurseOfGigantism ? breakRadius + 1 : breakRadius,
                    (breakState) -> {
                        double hardness = breakState.getHardness(null, null);

                        // special-case infested blocks for mining
                        boolean isEffective = player.getMainHandStack().isEffectiveOn(breakState) || breakState.getBlock() instanceof InfestedBlock;

                        // ensure hardness is within reasonable bounds of the original hardness, and is over 0 (unless the state is an infested block, special case)
                        boolean verifyHardness = hardness < originHardness * 5 && (breakState.getBlock() instanceof InfestedBlock || hardness > 0);
                        return isEffective && verifyHardness;
                    },
                    (tool, input) -> {
                        if (tool.getItem().equals(Items.FIERY)) {
                            Optional<SmeltingRecipe> cooked = world.getRecipeManager().getFirstMatch(
                                    RecipeType.SMELTING,
                                    new SimpleInventory(input),
                                    world
                            );

                            if (cooked.isPresent()) {
                                return cooked.get().getOutput().copy();
                            }
                        }

                        return input;
                    },
                    true
            );
        }

        return true;
    }
}
