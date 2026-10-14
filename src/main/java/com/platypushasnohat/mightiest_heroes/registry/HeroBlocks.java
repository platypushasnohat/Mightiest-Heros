package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HeroBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MightiestHeroes.MOD_ID);
    public static final List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();

    public static final DeferredBlock<Block> VIBRANIUM_BLOCK = registerBlockNoLang("vibranium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_VIBRANIUM_BLOCK = registerBlockNoLang("raw_vibranium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)));
    public static final DeferredBlock<Block> VIBRANIUM_ORE = registerBlock("vibranium_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> DEEPSLATE_VIBRANIUM_ORE = registerBlock("deepslate_vibranium_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        BLOCK_TRANSLATIONS.add(block);
        HeroItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        HeroItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}
