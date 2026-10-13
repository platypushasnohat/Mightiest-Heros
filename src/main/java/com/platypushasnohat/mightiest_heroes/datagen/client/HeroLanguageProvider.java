package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.MightiestHeroesTab;
import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
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

        this.add(HeroItems.DISABLED_VIBRANIUM_SHIELD.get(), "Vibranium Shield");
    }
}
