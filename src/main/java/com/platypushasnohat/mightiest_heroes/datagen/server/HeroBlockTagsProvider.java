package com.platypushasnohat.mightiest_heroes.datagen.server;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HeroBlockTagsProvider extends BlockTagsProvider {

    public HeroBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, MightiestHeroes.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                HeroBlocks.DEEPSLATE_VIBRANIUM_ORE.get(),
                HeroBlocks.VIBRANIUM_ORE.get(),
                HeroBlocks.VIBRANIUM_BLOCK.get(),
                HeroBlocks.RAW_VIBRANIUM_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                HeroBlocks.DEEPSLATE_VIBRANIUM_ORE.get(),
                HeroBlocks.VIBRANIUM_ORE.get(),
                HeroBlocks.VIBRANIUM_BLOCK.get(),
                HeroBlocks.RAW_VIBRANIUM_BLOCK.get()
        );
    }
}
