package com.platypushasnohat.mightiest_heroes.events;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

@EventBusSubscriber(modid = MightiestHeroes.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getDamageSource();
        Entity sourceEntity = damageSource.getEntity();
        if (!damageSource.is(DamageTypeTags.IS_PROJECTILE) && entity.getUseItem().is(HeroItems.VIBRANIUM_SHIELD) && sourceEntity != null) {
            VibraniumShield.applyKnockbackEffects(entity, sourceEntity, entity.getUseItem(), damageSource, 0.4F);
        }
    }
}
