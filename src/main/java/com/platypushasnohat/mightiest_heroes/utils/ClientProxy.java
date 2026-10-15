package com.platypushasnohat.mightiest_heroes.utils;

import com.platypushasnohat.mightiest_heroes.client.sounds.VibraniumShieldSoundInstance;
import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.sinew.mixins.client.SoundEngineAccessor;
import com.platypushasnohat.sinew.mixins.client.SoundManagerAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();

    public static final byte VIBRANIUM_SHIELD_SOUND = 0;

    @Override
    public void playSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity entity && !entity.level().isClientSide) {
            return;
        }
        if (type == VIBRANIUM_SHIELD_SOUND) {
            if (soundEmitter instanceof VibraniumShield shield) {
                VibraniumShieldSoundInstance sound;
                AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(shield.getId());
                if (oldSound == null || !(oldSound instanceof VibraniumShieldSoundInstance soundInstance && soundInstance.isSameEntity(shield))) {
                    sound = new VibraniumShieldSoundInstance(shield);
                    ENTITY_SOUND_INSTANCE_MAP.put(shield.getId(), sound);
                } else {
                    sound = soundInstance;
                }
                if (!this.isSoundPlaying(sound) && sound.canPlaySound()) {
                    Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                }
            }
        }
    }

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
    }

    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        SoundEngine soundEngine = ((SoundManagerAccessor) soundManager).getSoundEngine();
        SoundEngineAccessor engineAccessor = (SoundEngineAccessor) soundEngine;
        return engineAccessor.getQueuedTickableSounds().contains(sound) || engineAccessor.getTickingSounds().contains(sound);
    }
}
