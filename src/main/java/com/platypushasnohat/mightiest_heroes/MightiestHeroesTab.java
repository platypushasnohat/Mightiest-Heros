package com.platypushasnohat.mightiest_heroes;

import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MightiestHeroesTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MightiestHeroes.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MIGHTIEST_HEROES_TAB = CREATIVE_MODE_TABS.register("mightiest_heroes_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(HeroItems.VIBRANIUM_SHIELD.get()))
                    .title(Component.translatable("creative_tab.mightiest_heroes"))
                    .displayItems((parameters, output) -> {
                        output.accept(HeroBlocks.VIBRANIUM_ORE.get());
                        output.accept(HeroBlocks.DEEPSLATE_VIBRANIUM_ORE.get());
                        output.accept(HeroItems.RAW_VIBRANIUM.get());
                        output.accept(HeroBlocks.RAW_VIBRANIUM_BLOCK.get());
                        output.accept(HeroItems.VIBRANIUM_INGOT.get());
                        output.accept(HeroBlocks.VIBRANIUM_BLOCK.get());
                        output.accept(HeroItems.SUPER_SOLDIER_MASK.get());
                        output.accept(HeroItems.SUPER_SOLDIER_CHESTPLATE.get());
                        output.accept(HeroItems.SUPER_SOLDIER_LEGGINGS.get());
                        output.accept(HeroItems.SUPER_SOLDIER_BOOTS.get());
                        output.accept(HeroItems.VIBRANIUM_SHIELD.get());
                    })
                    .build());

}
