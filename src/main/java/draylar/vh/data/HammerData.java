package draylar.vh.data;

import draylar.staticcontent.api.ContentData;
import draylar.vh.VanillaHammers;
import draylar.vh.item.ExtendedHammerItem;
import draylar.vh.material.CustomToolMaterial;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Set;

public class HammerData implements ContentData {

    private final String id;
    private final int miningLevel;
    private final int durability;
    private final float blockBreakSpeed;
    private final float attackDamage;
    private final float attackSpeed;
    private final int enchantability;
    private final Identifier repairIngredient;
    private final boolean isFireImmune;
    private final boolean smelts;
    private final int breakRadius;
    private final boolean isExtra;
    private final int burnTime;
    private final boolean hasExtraKnockback;
    private final Identifier group;
    private transient ItemGroup cachedGroup;

    public static final Set<ItemStack> ENTRY_SET = ItemStackSet.create();

    public HammerData(String id, int miningLevel, int durability, float blockBreakSpeed, float attackDamage, float attackSpeed, int enchantability, Identifier repairIngredient, boolean isFireImmune, boolean smelts, int breakRadius, boolean isExtra, int burnTime, boolean hasExtraKnockback, Identifier group) {
        this.id = id;
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.blockBreakSpeed = blockBreakSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.isFireImmune = isFireImmune;
        this.smelts = smelts;
        this.breakRadius = breakRadius;
        this.isExtra = isExtra;
        this.burnTime = burnTime;
        this.hasExtraKnockback = hasExtraKnockback;
        this.group = group;
    }

    @Override
    public void register(Identifier identifier) {
        // only register if the hammer is not extra (wood -> diamond), or if it is extra (lapis, emerald, ...) and the option is enabled
        if (!isExtra() || isExtra() && VanillaHammers.CONFIG.enableExtraMaterials) {

            // setup settings
            Item.Settings settings = new Item.Settings();
            if (isFireImmune()) {
                settings.fireproof();
            }

            // check for hammer autosmelt
            // create hammer with settings
            ExtendedHammerItem hammerItem = new ExtendedHammerItem(
                    CustomToolMaterial.from(this),
                    0,
                    getAttackSpeed() == 0 ? -2.4f : getAttackSpeed(),
                    settings,
                    getBreakRadius() == 0 ? 1 : getBreakRadius(),
                    this
            );

            // burn time for furnace fuel
            if (getBurnTime() > 0) {
                FuelRegistry.INSTANCE.add(hammerItem, getBurnTime());
            }

            // add hammer to tag
            String path = getId() + "_hammer";
            Identifier registryID = path.contains(":") ? new Identifier(path) : VanillaHammers.id(path);
            Registry.register(Registries.ITEM, registryID, hammerItem);

            // add hammer to item group
            ENTRY_SET.add(hammerItem.getDefaultStack());
        }
    }

    public ItemGroup getGroup() {
        // attempt to get group from string
        if (cachedGroup == null && group != null) {
            for (ItemGroup itemGroup : ItemGroups.getGroups()) {
                if (itemGroup.getId().equals(group)) {
                    cachedGroup = itemGroup;
                    break;
                }
            }
        }

        return cachedGroup;
    }

    public boolean hasExtraKnockback() {
        return hasExtraKnockback;
    }

    public boolean canSmelt() {
        return smelts;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public String getId() {
        return id;
    }

    public int getBreakRadius() {
        return breakRadius;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getDurability() {
        return durability;
    }

    public float getBlockBreakSpeed() {
        return blockBreakSpeed * (float) VanillaHammers.CONFIG.breakSpeedMultiplier;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public Identifier getRepairIngredient() {
        return repairIngredient;
    }

    public boolean isFireImmune() {
        return isFireImmune;
    }
}
