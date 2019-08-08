package com.github.draylar.vh.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockBreaker {

    /**
     * Breaks blocks within the given radius on the axis the player is facing.
     * If the player is facing the X axis and the radius is 1, a 3x3 area will be destroyed on the X axis.
     * @param world world the player is in
     * @param playerEntity the player
     * @param radius radius to break
     * @param breakValidator check to see if a block can be broken
     */
    public static void breakInRadius(World world, PlayerEntity playerEntity, int radius, BreakValidator breakValidator, boolean damageTool) {
        if(!world.isClient) {
            List<BlockPos> brokenBlocks = getBreakBlocks(world, playerEntity, radius);
            for(BlockPos pos : brokenBlocks) {
                BlockState state = world.getBlockState(pos);
                if(breakValidator.canBreak(state)) {
                    world.breakBlock(pos, false);

                    if(!playerEntity.isCreative()) {
                        BlockPos offsetPos = new BlockPos(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
                        dropItems(world, Block.getDroppedStacks(state, (ServerWorld) world, pos, null, playerEntity, playerEntity.getMainHandStack()), offsetPos);
                        state.onStacksDropped(world, pos, playerEntity.getMainHandStack());
                    }

                    if (damageTool) {
                        playerEntity.inventory.getMainHandStack().damage(1, playerEntity, player -> { });
                    }
                }
            }
        }
    }

    private static void dropItems(World world, List<ItemStack> stacks, BlockPos pos) {
        for(ItemStack stack : stacks) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(itemEntity);
        }
    }


    /**
     * Returns a list of the blocks that would be broken in breakInRadius, but doesn't break them.
     * @param world world of player
     * @param playerEntity player breaking
     * @param radius radisu to break in
     * @return a list of blocks that would be broken with the given radius and tool
     */
    public static List<BlockPos> getBreakBlocks(World world, PlayerEntity playerEntity, int radius) {
        ArrayList<BlockPos> potentialBrokenBlocks = new ArrayList<>();

        Vec3d cameraPos = playerEntity.getCameraPosVec(1);
        Vec3d rotation = playerEntity.getRotationVec(1);
        Vec3d combined = cameraPos.add(rotation.x * 5, rotation.y * 5, rotation.z * 5);

        BlockHitResult blockHitResult = world.rayTrace(new RayTraceContext(cameraPos, combined, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.ANY, playerEntity));

        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            Direction.Axis axis = blockHitResult.getSide().getAxis();
            ArrayList<BlockPos> positions = new ArrayList<>();

            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        positions.add(new BlockPos(x, y, z));
                    }
                }
            }

            BlockPos origin = blockHitResult.getBlockPos();

            for(BlockPos pos : positions) {
                if(axis == Direction.Axis.Y) {
                    if(pos.getY() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                    }
                }

                else if (axis == Direction.Axis.X) {
                    if(pos.getX() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                    }
                }

                else if (axis == Direction.Axis.Z) {
                    if(pos.getZ() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                    }
                }
            }
        }

        return potentialBrokenBlocks;
    }

    private BlockBreaker() {
        // NO-OP
    }
}
