package com.platypushasnohat.mightiest_heroes.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class VibraniumShieldItem extends Item implements Equipable {

    public VibraniumShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack otherStack) {
        return super.isValidRepairItem(stack, otherStack);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }
}
