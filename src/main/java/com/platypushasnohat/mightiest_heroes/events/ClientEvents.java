package com.platypushasnohat.mightiest_heroes.events;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.client.models.SuperSoldierArmorModel;
import com.platypushasnohat.mightiest_heroes.client.models.VibraniumShieldModel;
import com.platypushasnohat.mightiest_heroes.client.renderer.VibraniumShieldRenderer;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MightiestHeroes.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return VibraniumShieldRenderer.INSTANCE;
            }
        }, HeroItems.VIBRANIUM_SHIELD);

        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> properties) {
                return SuperSoldierArmorModel.INSTANCE;
            }
        }, HeroItems.SUPER_SOLDIER_MASK, HeroItems.SUPER_SOLDIER_CHESTPLATE, HeroItems.SUPER_SOLDIER_LEGGINGS, HeroItems.SUPER_SOLDIER_BOOTS);
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HeroModelLayers.VIBRANIUM_SHIELD, VibraniumShieldModel::createBodyLayer);
    }
}
