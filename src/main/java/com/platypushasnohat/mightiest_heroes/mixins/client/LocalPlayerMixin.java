package com.platypushasnohat.mightiest_heroes.mixins.client;

import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Unique
    private static final float VIBRANIUM_SHIELD_MOVEMENT_MULTIPLIER = 0.5F;

    @ModifyConstant(method = "aiStep", constant = @Constant(floatValue = 0.2F, ordinal = 0))
    private float mightiestHeroes$moveFasterWhileBlockingLeft(float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (this.playerHasVibraniumShield(player)) {
            return VIBRANIUM_SHIELD_MOVEMENT_MULTIPLIER;
        }
        return value;
    }

    @ModifyConstant(method = "aiStep", constant = @Constant(floatValue = 0.2F, ordinal = 1))
    private float mightiestHeroes$moveFasterWhileBlockingForward(float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (this.playerHasVibraniumShield(player)) {
            return VIBRANIUM_SHIELD_MOVEMENT_MULTIPLIER;
        }
        return value;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    private boolean playerHasVibraniumShield(LocalPlayer player) {
        if (player.isUsingItem()) {
            ItemStack useItem = player.getUseItem();
            return useItem.is(HeroItems.VIBRANIUM_SHIELD);
        }
        return false;
    }
}