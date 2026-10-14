package com.platypushasnohat.mightiest_heroes.items;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroArmorDefinitions;
import com.platypushasnohat.sinew.item.SkinLayerHidingArmorItem;
import net.minecraft.resources.ResourceLocation;

public class SuperSoldierArmorItem extends SkinLayerHidingArmorItem {

    public static final ResourceLocation SET_BONUS = MightiestHeroes.location("super_soldier_set_bonus");

    public SuperSoldierArmorItem(Type type, Properties properties) {
        super(type, properties, HeroArmorDefinitions.SUPER_SOLDIER);
    }

//    public static void applySetBonus(LivingEntity living) {
//        AttributeModifier healthModifier = new AttributeModifier(SET_BONUS, 8, AttributeModifier.Operation.ADD_VALUE);
//        AttributeInstance attribute = living.getAttribute(Attributes.MAX_HEALTH);
//        if (attribute != null) {
//            if (SuperSoldierArmorItem.wearingFullSet(living) && !attribute.hasModifier(SET_BONUS)) {
//                attribute.addTransientModifier(healthModifier);
//            }
//            else if (attribute.hasModifier(SET_BONUS)) {
//                attribute.removeModifier(SET_BONUS);
//            }
//        }
//    }
//
//    public static boolean wearingFullSet(LivingEntity entity) {
//        return entity.getItemBySlot(EquipmentSlot.HEAD).is(HeroItems.SUPER_SOLDIER_MASK.get())
//                && entity.getItemBySlot(EquipmentSlot.CHEST).is(HeroItems.SUPER_SOLDIER_CHESTPLATE.get())
//                && entity.getItemBySlot(EquipmentSlot.LEGS).is(HeroItems.SUPER_SOLDIER_LEGGINGS.get())
//                && entity.getItemBySlot(EquipmentSlot.FEET).is(HeroItems.SUPER_SOLDIER_BOOTS.get());
//    }
}
