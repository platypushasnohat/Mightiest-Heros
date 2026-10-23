package com.platypushasnohat.mightiest_heroes.items;

import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import com.platypushasnohat.sinew.item.DisabledItem;
import com.platypushasnohat.sinew.item.MovementModifyingItem;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class VibraniumShieldItem extends Item implements Equipable, MovementModifyingItem {

    private static final float BASE_THROWN_SHIELD_DAMAGE = 6.0F;
    private static final int THROWN_SHIELD_RETURN_TIME = 20;
    private static final int SHIELD_COOLDOWN = 40;

    private int swingSoundCooldown = 0;

    public VibraniumShieldItem(Properties properties) {
        super(properties.durability(1600).stacksTo(1).component(DataComponents.TOOL, createToolProperties()).attributes(createAttributes(5.0F, -2.4F)));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    private void throwShield(ItemStack stack, Level level, Player player, InteractionHand hand) {
        int slot = player.getInventory().selected;
        if (hand == InteractionHand.OFF_HAND) {
            slot = Inventory.SLOT_OFFHAND;
        }
        if (!level.isClientSide) {
            VibraniumShield shield = new VibraniumShield(level, player);
            shield.setOwner(player);
            shield.setItem(stack);
            shield.setSlot(slot);
            shield.setReturnTime(THROWN_SHIELD_RETURN_TIME);
            shield.setDamage(BASE_THROWN_SHIELD_DAMAGE + this.getBaseThrownShieldDamage(player));
            shield.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.0F);
            level.addFreshEntity(shield);
            level.playSound(null, shield.blockPosition(), HeroSoundEvents.VIBRANIUM_SHIELD_SWIPE.get(), SoundSource.PLAYERS, 1.0F, 1.25F * SinewSoundUtils.randomizePitch(level));
            if (player instanceof ServerPlayer serverPlayer) {
                DisabledItem.disableItem(serverPlayer, slot, HeroItems.DISABLED_VIBRANIUM_SHIELD);
            }
        }
    }

    private float getBaseThrownShieldDamage(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier itemModifier = null;
        if (attribute != null) {
            itemModifier = attribute.getModifier(BASE_ATTACK_DAMAGE_ID);
        }
        double itemDamage = itemModifier != null ? itemModifier.amount() : 0.0D;
        return Math.round((float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) - itemDamage);
    }

    public static void catchShield(VibraniumShield shield, ItemStack stack, LivingEntity owner) {
        if (owner instanceof ServerPlayer player) {
            player.getCooldowns().addCooldown(stack.getItem(), SHIELD_COOLDOWN);
            DisabledItem.enableItem(player, shield.getSlot());
        }
        shield.level().playSound(null, shield.getX(), shield.getY(), shield.getZ(), HeroSoundEvents.VIBRANIUM_SHIELD_CATCH.get(), SoundSource.PLAYERS, 1.0F, SinewSoundUtils.randomizePitch(shield.level()));
        shield.discard();
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (this.swingSoundCooldown > 0) {
            this.swingSoundCooldown--;
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.playSound(HeroSoundEvents.VIBRANIUM_SHIELD_IMPACT.get(), 1.0F, SinewSoundUtils.randomizePitch(attacker));
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        if (!entity.level().isClientSide && this.swingSoundCooldown == 0) {
            this.swingSoundCooldown = 10;
            if (entity instanceof ServerPlayer) {
                entity.level().playSound(null, entity.blockPosition(), HeroSoundEvents.VIBRANIUM_SHIELD_SWIPE.get(), SoundSource.PLAYERS, 1.0F, SinewSoundUtils.randomizePitch(entity));
            }
        }
        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            player.getCooldowns().addCooldown(stack.getItem(), 10);
            return InteractionResultHolder.pass(stack);
        } else if (player.isShiftKeyDown()) {
            player.startUsingItem(hand);
            return InteractionResultHolder.pass(stack);
        } else {
            player.swing(hand, true);
            this.throwShield(stack, level, player, hand);
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return InteractionResultHolder.success(stack);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(HeroItems.VIBRANIUM_INGOT) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility) || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    @Override
    public float getUseMovementMultiplier() {
        return 0.5F;
    }

    @Override
    public boolean canSprintWhileUsing() {
        return false;
    }
}
