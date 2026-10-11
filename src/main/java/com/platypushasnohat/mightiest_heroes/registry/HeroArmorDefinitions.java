package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.sinew.item.ArmorDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeroArmorDefinitions {

    public static final ArmorDefinition SUPER_SOLDIER = new ArmorDefinition.Builder()
            .material(HeroArmorMaterials.SUPER_SOLDIER)
            .attribute(Attributes.MAX_HEALTH, 3.0F, AttributeModifier.Operation.ADD_VALUE)
            .attribute(Attributes.ATTACK_DAMAGE, 1.5F, AttributeModifier.Operation.ADD_VALUE)
            .attribute(Attributes.MOVEMENT_SPEED, 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .texture(slot -> slot == EquipmentSlot.LEGS ? MightiestHeroes.location("textures/models/armor/super_soldier_layer_2.png") : MightiestHeroes.location("textures/models/armor/super_soldier_layer_1.png"))
            .build();

}
