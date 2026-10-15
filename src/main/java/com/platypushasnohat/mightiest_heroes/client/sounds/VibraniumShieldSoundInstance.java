package com.platypushasnohat.mightiest_heroes.client.sounds;

import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class VibraniumShieldSoundInstance extends AbstractTickableSoundInstance {

    private final VibraniumShield shield;

    public VibraniumShieldSoundInstance(VibraniumShield shield) {
        super(HeroSoundEvents.VIBRANIUM_SHIELD_LOOP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.shield = shield;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.looping = true;
    }

    @Override
    public boolean canPlaySound() {
        return this.shield.isAlive() && !this.shield.isSilent();
    }

    @Override
    public void tick() {
        if (!this.shield.isAlive()) {
            this.stop();
        }
        this.x = (float) this.shield.getX();
        this.y = (float) this.shield.getY();
        this.z = (float) this.shield.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    public boolean isSameEntity(VibraniumShield shield) {
        return this.shield.isAlive() && this.shield.getId() == shield.getId();
    }
}