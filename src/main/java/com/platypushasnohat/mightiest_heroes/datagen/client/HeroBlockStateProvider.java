package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import com.platypushasnohat.sinew.datagen.client.SinewBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HeroBlockStateProvider extends SinewBlockStateProvider {

    public HeroBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MightiestHeroes.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.block(HeroBlocks.VIBRANIUM_ORE);
        this.block(HeroBlocks.DEEPSLATE_VIBRANIUM_ORE);
        this.block(HeroBlocks.VIBRANIUM_BLOCK);
        this.block(HeroBlocks.RAW_VIBRANIUM_BLOCK);
    }
}
