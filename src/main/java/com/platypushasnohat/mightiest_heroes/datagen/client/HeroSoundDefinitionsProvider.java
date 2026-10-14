package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import com.platypushasnohat.sinew.datagen.client.SinewSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HeroSoundDefinitionsProvider extends SinewSoundDefinitionsProvider {

    public HeroSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MightiestHeroes.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.registerSound(HeroSoundEvents.VIBRANIUM_SHIELD_IMPACT,
                sound(MightiestHeroes.location("entity/vibranium_shield/impact1")),
                sound(MightiestHeroes.location("entity/vibranium_shield/impact2")),
                sound(MightiestHeroes.location("entity/vibranium_shield/impact3"))
        );

        this.registerSound(HeroSoundEvents.VIBRANIUM_SHIELD_SWIPE,
                sound(MightiestHeroes.location("entity/vibranium_shield/swipe1")),
                sound(MightiestHeroes.location("entity/vibranium_shield/swipe2")),
                sound(MightiestHeroes.location("entity/vibranium_shield/swipe3"))
        );

        this.registerSound(HeroSoundEvents.VIBRANIUM_SHIELD_CATCH,
                sound(MightiestHeroes.location("entity/vibranium_shield/catch"))
        );
    }
}
