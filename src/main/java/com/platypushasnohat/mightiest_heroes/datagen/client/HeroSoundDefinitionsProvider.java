package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.sinew.datagen.client.SinewSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HeroSoundDefinitionsProvider extends SinewSoundDefinitionsProvider {

    public HeroSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MightiestHeroes.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

    }
}
