package com.platypushasnohat.mightiest_heroes.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected ItemStack useItem;

    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"))
    private void mightiestHeroes$hurt(Level level, Entity entity, byte state, Operation<Void> original) {
        if (!this.useItem.is(HeroItems.VIBRANIUM_SHIELD.get())) {
            original.call(level, entity, state);
        } else if (entity instanceof Player player) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), HeroSoundEvents.VIBRANIUM_SHIELD_BLOCK.get(), player.getSoundSource(), 1.0F, SinewSoundUtils.randomizePitch(level));
        }
    }
}
