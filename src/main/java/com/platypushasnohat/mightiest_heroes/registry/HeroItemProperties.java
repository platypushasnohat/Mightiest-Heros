package com.platypushasnohat.mightiest_heroes.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class HeroItemProperties {

    public static void registerItemProperties() {
        ItemProperties.register(HeroItems.VIBRANIUM_SHIELD.get(), ResourceLocation.withDefaultNamespace("blocking"), (stack, level, entity, hash) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
