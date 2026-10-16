package com.platypushasnohat.mightiest_heroes.items;

import com.platypushasnohat.mightiest_heroes.registry.HeroArmorDefinitions;
import com.platypushasnohat.sinew.item.SkinLayerHidingArmorItem;

public class GoblinArmorItem extends SkinLayerHidingArmorItem {

    public GoblinArmorItem(Type type, Properties properties) {
        super(type, properties, HeroArmorDefinitions.GOBLIN);
    }
}
