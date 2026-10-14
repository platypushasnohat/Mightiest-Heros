package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HeroSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, MightiestHeroes.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> VIBRANIUM_SHIELD_IMPACT = registerSoundEvent("vibranium_shield_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> VIBRANIUM_SHIELD_SWIPE = registerSoundEvent("vibranium_shield_swipe");
    public static final DeferredHolder<SoundEvent, SoundEvent> VIBRANIUM_SHIELD_CATCH = registerSoundEvent("vibranium_shield_catch");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String soundName) {
        return SOUND_EVENT.register(soundName, () -> SoundEvent.createVariableRangeEvent(MightiestHeroes.location(soundName)));
    }
}
