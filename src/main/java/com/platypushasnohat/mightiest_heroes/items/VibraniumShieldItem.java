package com.platypushasnohat.mightiest_heroes.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/* todo:
    - Add throwing
    - Add server side blocking behavior
*/
public class VibraniumShieldItem extends Item implements Equipable {

    public VibraniumShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof Player player && level.isClientSide) {
            InteractionHand hand = stack == player.getOffhandItem() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            if (player.isShiftKeyDown() && !player.isUsingItem()) {
                player.startUsingItem(hand);
            }
            else if (player.isUsingItem() && player.getUseItem() == stack) {
                player.stopUsingItem();
            }
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
}
