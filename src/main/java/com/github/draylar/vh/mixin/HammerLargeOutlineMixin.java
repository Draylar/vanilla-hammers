package com.github.draylar.vh.mixin;

import com.github.draylar.vh.hammer.HammerItem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class HammerLargeOutlineMixin
{
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private Camera camera;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawHighlightedBlockOutline(Lnet/minecraft/client/render/Camera;Lnet/minecraft/util/hit/HitResult;I)V"), method = "renderCenter")
    public void renderCenter(float float_1, long long_1, CallbackInfo ci)
    {
        if (this.client.player.inventory.getMainHandStack().getItem() instanceof HammerItem)
        {
            if (client.hitResult instanceof BlockHitResult)
            {
                BlockHitResult hitResult = (BlockHitResult) client.hitResult;
                BlockPos blockPos_1 = hitResult.getBlockPos();
                BlockState blockState_1 = client.world.getBlockState(blockPos_1);

                if (!blockState_1.isAir() && client.world.getWorldBorder().contains(blockPos_1))
                {
                    if (hitResult.getSide().getAxis() == Direction.Axis.Y)
                    {
                        GlStateManager.enableBlend();
                        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        GlStateManager.lineWidth(Math.max(2.5F, (float) this.client.window.getFramebufferWidth() / 1920.0F * 2.5F));
                        GlStateManager.disableTexture();
                        GlStateManager.depthMask(false);
                        GlStateManager.matrixMode(5889);
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(1.0F, 1.0F, 0.999F);
                        double double_1 = camera.getPos().x;
                        double double_2 = camera.getPos().y;
                        double double_3 = camera.getPos().z;

                        // -x is west
                        // x is east
                        // -z is north
                        // z is south

                        VoxelShape shape = VoxelShapes.empty();

                        for (int x = -1; x < 2; x++)
                        {
                            for (int z = -1; z < 2; z++)
                            {
                                if(client.world.getBlockState(blockPos_1.add(x, 0, z)) != Blocks.AIR.getDefaultState())
                                {
                                    shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(x, 0, z)).getOutlineShape(client.world, blockPos_1.add(x, 0, z)).offset(x, 0, z));
                                }
                            }
                        }

                        WorldRenderer.drawShapeOutline(shape, (double) blockPos_1.getX() - double_1, (double) blockPos_1.getY() - double_2, (double) blockPos_1.getZ() - double_3, 0.0F, 0.0F, 0.0F, 0.4F);

                        GlStateManager.popMatrix();
                        GlStateManager.matrixMode(5888);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableTexture();
                        GlStateManager.disableBlend();
                    }

                    else if (hitResult.getSide().getAxis() == Direction.Axis.X)
                    {
                        GlStateManager.enableBlend();
                        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        GlStateManager.lineWidth(Math.max(2.5F, (float) this.client.window.getFramebufferWidth() / 1920.0F * 2.5F));
                        GlStateManager.disableTexture();
                        GlStateManager.depthMask(false);
                        GlStateManager.matrixMode(5889);
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(1.0F, 1.0F, 0.999F);
                        double double_1 = camera.getPos().x;
                        double double_2 = camera.getPos().y;
                        double double_3 = camera.getPos().z;

                        VoxelShape shape = VoxelShapes.empty();

                        for (int y = -1; y < 2; y++)
                        {
                            for (int z = -1; z < 2; z++)
                            {
                                if(client.world.getBlockState(blockPos_1.add(0, y, z)) != Blocks.AIR.getDefaultState())
                                {
                                    shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(0, y, z)).getOutlineShape(client.world, blockPos_1.add(0, y, z)).offset(0, y, z));
                                }
                            }
                        }

                        WorldRenderer.drawShapeOutline(shape, (double) blockPos_1.getX() - double_1, (double) blockPos_1.getY() - double_2, (double) blockPos_1.getZ() - double_3, 0.0F, 0.0F, 0.0F, 0.4F);

                        GlStateManager.popMatrix();
                        GlStateManager.matrixMode(5888);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableTexture();
                        GlStateManager.disableBlend();
                    }

                    else if (hitResult.getSide().getAxis() == Direction.Axis.Z)
                    {
                        GlStateManager.enableBlend();
                        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        GlStateManager.lineWidth(Math.max(2.5F, (float) this.client.window.getFramebufferWidth() / 1920.0F * 2.5F));
                        GlStateManager.disableTexture();
                        GlStateManager.depthMask(false);
                        GlStateManager.matrixMode(5889);
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(1.0F, 1.0F, 0.999F);
                        double double_1 = camera.getPos().x;
                        double double_2 = camera.getPos().y;
                        double double_3 = camera.getPos().z;

                        VoxelShape shape = VoxelShapes.empty();

                        for (int x = -1; x < 2; x++)
                        {
                            for (int y = -1; y < 2; y++)
                            {
                                if(client.world.getBlockState(blockPos_1.add(x, y, 0)) != Blocks.AIR.getDefaultState())
                                {
                                    shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos_1.add(x, y, 0)).getOutlineShape(client.world, blockPos_1.add(x, y, 0)).offset(x, y, 0));
                                }
                            }
                        }

                        WorldRenderer.drawShapeOutline(shape, (double) blockPos_1.getX() - double_1, (double) blockPos_1.getY() - double_2, (double) blockPos_1.getZ() - double_3, 0.0F, 0.0F, 0.0F, 0.4F);

                        GlStateManager.popMatrix();
                        GlStateManager.matrixMode(5888);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableTexture();
                        GlStateManager.disableBlend();
                    }
                }
            }
        }
    }
}
