package draylar.vh.item;

import dev.draylar.magna.api.BlockProcessor;
import dev.draylar.magna.item.HammerItem;
import draylar.vh.data.HammerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class ExtendedHammerItem extends HammerItem {

    private final HammerData data;

    public ExtendedHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int breakRadius, HammerData data) {
        super(toolMaterial, attackDamage, attackSpeed, settings, breakRadius);
        this.data = data;
    }

    public HammerData getData() {
        return data;
    }

    @Override
    public BlockProcessor getProcessor(World world, PlayerEntity player, BlockPos pos, ItemStack heldStack) {
        if (data.canSmelt()) {
            return (tool, input) -> {
                Optional<SmeltingRecipe> cooked = world.getRecipeManager().getFirstMatch(
                        RecipeType.SMELTING,
                        new SimpleInventory(input),
                        world
                );

                if (cooked.isPresent()) {
                    return cooked.get().getOutput(world.getRegistryManager()).copy();
                }

                return input;
            };
        } else {
            return super.getProcessor(world, player, pos, heldStack);
        }
    }
}
