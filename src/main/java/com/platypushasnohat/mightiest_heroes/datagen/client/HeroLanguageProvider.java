package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.MightiestHeroesTab;
import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import com.platypushasnohat.mightiest_heroes.registry.HeroEntities;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import com.platypushasnohat.sinew.datagen.client.SinewLanguageProvider;
import net.minecraft.data.PackOutput;

public class HeroLanguageProvider extends SinewLanguageProvider {

    public HeroLanguageProvider(PackOutput output) {
        super(output, MightiestHeroes.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeTab(MightiestHeroesTab.MIGHTIEST_HEROES_TAB.get(), "Mightiest Heroes");
        HeroItems.ITEM_TRANSLATIONS.forEach(this::forItem);
        HeroBlocks.BLOCK_TRANSLATIONS.forEach(this::forBlock);
        HeroEntities.ENTITY_TRANSLATIONS.forEach(this::forEntity);

        this.addSound(HeroSoundEvents.VIBRANIUM_SHIELD_IMPACT, "Vibranium Shield impacts");
        this.addSound(HeroSoundEvents.VIBRANIUM_SHIELD_SWIPE, "Vibranium Shield swipes");
        this.addSound(HeroSoundEvents.VIBRANIUM_SHIELD_CATCH, "Vibranium Shield caught");

        this.add(HeroItems.DISABLED_VIBRANIUM_SHIELD.get(), "Vibranium Shield");

        this.add(HeroBlocks.VIBRANIUM_BLOCK.get(), "Block of Vibranium");
        this.add(HeroBlocks.RAW_VIBRANIUM_BLOCK.get(), "Block of Raw Vibranium");

    }
}
