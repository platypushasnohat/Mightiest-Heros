package com.platypushasnohat.mightiest_heroes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.client.models.VibraniumShieldModel;
import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import com.platypushasnohat.mightiest_heroes.registry.HeroModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VibraniumShieldRenderer extends EntityRenderer<VibraniumShield> {

    private static final ResourceLocation TEXTURE_LOCATION = MightiestHeroes.location("textures/entity/vibranium_shield/vibranium_shield.png");

    private static final float MIN_CAMERA_DISTANCE_SQUARED = Mth.square(3.5F);

    private final VibraniumShieldModel model;

    public VibraniumShieldRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new VibraniumShieldModel(context.bakeLayer(HeroModelLayers.VIBRANIUM_SHIELD));
    }

    @Override
    public void render(VibraniumShield entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < (double) MIN_CAMERA_DISTANCE_SQUARED)) {
            poseStack.pushPose();
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(0.0F, -1.501F, 0.0F);
            poseStack.translate(0.0F, -entity.getBbHeight() / 2, 0.0F);
            float ageInTicks = (float) entity.tickCount + partialTicks;
            VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(TEXTURE_LOCATION), false, entity.getItem().hasFoil());
            this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(VibraniumShield entity) {
        return TEXTURE_LOCATION;
    }
}
