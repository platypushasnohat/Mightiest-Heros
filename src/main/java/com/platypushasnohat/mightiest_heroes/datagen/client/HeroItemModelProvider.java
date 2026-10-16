package com.platypushasnohat.mightiest_heroes.datagen.client;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.sinew.datagen.client.SinewItemModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HeroItemModelProvider extends SinewItemModelProvider {

    public HeroItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MightiestHeroes.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {

        this.generatedItem(
                HeroItems.RAW_VIBRANIUM,
                HeroItems.VIBRANIUM_INGOT,
                HeroItems.SUPER_SOLDIER_MASK,
                HeroItems.SUPER_SOLDIER_CHESTPLATE,
                HeroItems.SUPER_SOLDIER_LEGGINGS,
                HeroItems.SUPER_SOLDIER_BOOTS,
                HeroItems.DISABLED_VIBRANIUM_SHIELD,
                HeroItems.GOBLIN_MASK,
                HeroItems.GOBLIN_CHESTPLATE,
                HeroItems.GOBLIN_LEGGINGS,
                HeroItems.GOBLIN_BOOTS
        );

        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof DeferredSpawnEggItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(MightiestHeroes.MOD_ID)) {
                this.withExistingParent(name(item), "item/template_spawn_egg");
            }
        }
    }
}
