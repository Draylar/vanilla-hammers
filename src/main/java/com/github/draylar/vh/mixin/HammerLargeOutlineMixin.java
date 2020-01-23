package com.github.draylar.vh.mixin;

import com.github.draylar.vh.api.HammerItem;
import com.github.draylar.vh.config.VanillaHammersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class HammerLargeOutlineMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private double lastCameraX;
    @Shadow private double lastCameraY;
    @Shadow private double lastCameraZ;

    @Shadow private static void drawShapeOutline(MatrixStack matrixStack,
                                                 VertexConsumer vertexConsumer,
                                                 VoxelShape voxelShape,
                                                 double d,
                                                 double e,
                                                 double f,
                                                 float g,
                                                 float h,
                                                 float i,
                                                 float j) { }

    @Inject(at = @At("HEAD"), method = "drawBlockOutline", cancellable = true)
    private void drawBlockOutline(MatrixStack matrixStack,
                                  VertexConsumer vertexConsumer,
                                  Entity entity,
                                  double d,
                                  double e,
                                  double f,
                                  BlockPos blockPos,
                                  BlockState blockState,
                                  CallbackInfo ci) {
        VanillaHammersConfig config = AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig();

        if (this.client.player.inventory.getMainHandStack().getItem() instanceof HammerItem && !config.alwaysShowSingleBlockHitbox) {
            if (!config.showSingleBlockWhenSneaking || !client.player.isSneaking()) {
                if (client.crosshairTarget instanceof BlockHitResult) {
                    BlockHitResult crosshairTarget = (BlockHitResult) client.crosshairTarget;
                    BlockPos blockPos_1 = crosshairTarget.getBlockPos();
                    BlockState blockState_1 = client.world.getBlockState(blockPos_1);

                    if (!blockState_1.isAir() && client.world.getWorldBorder().contains(blockPos_1)) {
                        if (crosshairTarget.getSide().getAxis() == Direction.Axis.Y) {
                            // -x is west
                            // x is east
                            // -z is north
                            // z is south

                            VoxelShape shape = VoxelShapes.empty();

                            for (int x = -1; x < 2; x++) {
                                for (int z = -1; z < 2; z++) {
                                    if (client.world.getBlockState(blockPos_1.add(x, 0, z)) != Blocks.AIR.getDefaultState() || AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableFull3x3) {
                                        if (!AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().fullBlockHitbox) {
                                            shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(x, 0, z)).getOutlineShape(client.world, blockPos_1.add(x, 0, z)).offset(x, 0, z));
                                        } else {
                                            shape = VoxelShapes.union(shape, VoxelShapes.fullCube().offset(x, 0, z));
                                        }
                                    }
                                }
                            }

                            drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos_1.getX() - lastCameraX, (double) blockPos_1.getY() - lastCameraY, (double) blockPos_1.getZ() - lastCameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
                            ci.cancel();
                        } else if (crosshairTarget.getSide().getAxis() == Direction.Axis.X) {
                            VoxelShape shape = VoxelShapes.empty();

                            for (int y = -1; y < 2; y++) {
                                for (int z = -1; z < 2; z++) {
                                    if (client.world.getBlockState(blockPos_1.add(0, y, z)) != Blocks.AIR.getDefaultState() || AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableFull3x3) {
                                        if (!AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().fullBlockHitbox) {
                                            shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(0, y, z)).getOutlineShape(client.world, blockPos_1.add(0, y, z)).offset(0, y, z));
                                        } else {
                                            shape = VoxelShapes.union(shape, VoxelShapes.fullCube().offset(0, y, z));
                                        }
                                    }
                                }
                            }

                            drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos_1.getX() - lastCameraX, (double) blockPos_1.getY() - lastCameraY, (double) blockPos_1.getZ() - lastCameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
                            ci.cancel();
                        } else if (crosshairTarget.getSide().getAxis() == Direction.Axis.Z) {
                            VoxelShape shape = VoxelShapes.empty();

                            for (int x = -1; x < 2; x++) {
                                for (int y = -1; y < 2; y++) {
                                    if (client.world.getBlockState(blockPos_1.add(x, y, 0)) != Blocks.AIR.getDefaultState() || AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().enableFull3x3) {
                                        if (!AutoConfig.getConfigHolder(VanillaHammersConfig.class).getConfig().fullBlockHitbox) {
                                            shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(x, y, 0)).getOutlineShape(client.world, blockPos_1.add(x, y, 0)).offset(x, y, 0));
                                        } else {
                                            shape = VoxelShapes.union(shape, VoxelShapes.fullCube().offset(x, y, 0));
                                        }
                                    }
                                }
                            }

                            drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos_1.getX() - lastCameraX, (double) blockPos_1.getY() - lastCameraY, (double) blockPos_1.getZ() - lastCameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }
}