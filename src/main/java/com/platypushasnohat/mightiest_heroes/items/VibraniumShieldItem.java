package com.platypushasnohat.mightiest_heroes.items;

import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.sinew.item.DisabledItem;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class VibraniumShieldItem extends Item implements Equipable {

    public VibraniumShieldItem(Properties properties) {
        super(properties);
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            player.getCooldowns().addCooldown(stack.getItem(), 10);
            return InteractionResultHolder.pass(stack);
        } else if (player.isShiftKeyDown()) {
            player.startUsingItem(hand);
            return InteractionResultHolder.pass(stack);
        } else {
            int slot = player.getInventory().selected;
            if (hand == InteractionHand.OFF_HAND) {
                slot = Inventory.SLOT_OFFHAND;
            }
            if (!level.isClientSide) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                Vec3 position = player.position().add(0, player.getBbHeight() * 0.5F, 0);
                VibraniumShield shield = new VibraniumShield(level, position.x, position.y, position.z);
                shield.setOwner(player);
                shield.setItem(stack);
                shield.setReturnTime(15);
                shield.setDamage(10.0F);
                shield.setSlot(slot);
                shield.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.0F);
                level.addFreshEntity(shield);
                if (player instanceof ServerPlayer serverPlayer) {
                    DisabledItem.disableItem(serverPlayer, slot, HeroItems.DISABLED_VIBRANIUM_SHIELD);
                }
                level.playSound(null, shield.blockPosition(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 1.0F, SinewSoundUtils.randomizePitch(level));
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            player.swing(hand, true);
            return InteractionResultHolder.success(stack);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack otherStack) {
        return super.isValidRepairItem(stack, otherStack);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
    }
}
