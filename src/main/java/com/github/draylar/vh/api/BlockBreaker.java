package com.github.draylar.vh.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
     * Breaks blocks within the given radius in the direction the {@link PlayerEntity} is facing.
     *
     * <p>For example: If the {@link PlayerEntity} is facing in the X axis direction and the radius is 1, a 3x3 area will be destroyed on the X axis.
     *
     * @param world world the player is in
     * @param player the player
     * @param radius radius to break
     * @param breakValidator check to see if a block can be broken
     */
    public static void breakInRadius(World world, PlayerEntity player, int radius, BreakValidator breakValidator, BlockProcessor smelter, boolean damageTool) {
        if(!world.isClient) {
            // collect all potential blocks to break and attempt to break them
            List<BlockPos> brokenBlocks = findPositions(world, player, radius);
            for(BlockPos pos : brokenBlocks) {
                BlockState state = world.getBlockState(pos);

                // ensure the tool or mechanic can break the given state
                if(breakValidator.canBreak(state)) {
                    world.breakBlock(pos, false);
                    state.getBlock().onBreak(world, pos, state, player);

                    // only drop items in creative
                    if(!player.isCreative()) {
                        BlockPos offsetPos = new BlockPos(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);

                        // obtain dropped stacks for the given block
                        List<ItemStack> droppedStacks = Block.getDroppedStacks(state, (ServerWorld) world, pos, null, player, player.getMainHandStack());
                        List<ItemStack> processed = new ArrayList<>();

                        // attempt to process stack for mechanics like autosmelt
                        droppedStacks.forEach(stack -> processed.add(smelter.process(player.inventory.getMainHandStack(), stack)));

                        // drop items
                        dropItems(world, processed, offsetPos);
                        state.onStacksDropped(world, pos, player.getMainHandStack());
                    }

                    if (damageTool) {
                        player.inventory.getMainHandStack().damage(1, player, p -> { });
                    }
                }
            }
        }
    }

    /**
     * Drops each {@link ItemStack} from the given {@link List} into the given {@link World} at the given {@link BlockPos}.
     *
     * @param world  world to drop items in
     * @param stacks  list of {@link ItemStack}s to drop in the world
     * @param pos  position to drop items at
     */
    private static void dropItems(World world, List<ItemStack> stacks, BlockPos pos) {
        for(ItemStack stack : stacks) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(itemEntity);
        }
    }

    /**
     * Returns a list of {@link BlockPos} in the given radius considering the {@link PlayerEntity}'s facing direction.
     *
     * @param world  world to check in
     * @param playerEntity  player that is collecting blocks
     * @param radius  radius to collect blocks in
     * @return  a list of blocks that would be broken with the given radius and tool
     */
    public static List<BlockPos> findPositions(World world, PlayerEntity playerEntity, int radius) {
        ArrayList<BlockPos> potentialBrokenBlocks = new ArrayList<>();

        Vec3d cameraPos = playerEntity.getCameraPosVec(1);
        Vec3d rotation = playerEntity.getRotationVec(1);
        Vec3d combined = cameraPos.add(rotation.x * 5, rotation.y * 5, rotation.z * 5);

        BlockHitResult blockHitResult = world.rayTrace(new RayTraceContext(cameraPos, combined, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, playerEntity));

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
