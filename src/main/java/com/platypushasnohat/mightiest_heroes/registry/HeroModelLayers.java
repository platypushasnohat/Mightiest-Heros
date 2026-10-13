package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class HeroModelLayers {

    public static final ModelLayerLocation VIBRANIUM_SHIELD = register("vibranium_shield");

    public static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    public static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(MightiestHeroes.location(name), layer);
    }
}
