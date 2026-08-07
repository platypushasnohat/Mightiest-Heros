package com.platypushasnohat.mightiest_heroes;

import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MightiestHeroesTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MightiestHeroes.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RAINBOW_REEF_TAB = CREATIVE_MODE_TABS.register("mightiest_heros_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(HeroItems.VIBRANIUM_SHIELD.get()))
                    .title(Component.translatable("creativetab.mightiest_heros_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(HeroItems.VIBRANIUM_SHIELD.get());
                    })
                    .build());

}
