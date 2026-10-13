package com.platypushasnohat.mightiest_heroes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.client.models.VibraniumShieldItemModel;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class VibraniumShieldItemRenderer extends BlockEntityWithoutLevelRenderer {

	private static final ResourceLocation VIBRANIUM_SHIELD_LOCATION = MightiestHeroes.location("textures/entity/vibranium_shield/vibranium_shield.png");

	public static final VibraniumShieldItemRenderer INSTANCE = new VibraniumShieldItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

	private final VibraniumShieldItemModel vibraniumShieldItemModel;

	public VibraniumShieldItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
		super(dispatcher, modelSet);
		this.vibraniumShieldItemModel = new VibraniumShieldItemModel(modelSet.bakeLayer(HeroModelLayers.VIBRANIUM_SHIELD_ITEM));
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay) {
		if (stack.is(HeroItems.VIBRANIUM_SHIELD.get())) {
			poseStack.pushPose();
			poseStack.translate(0.5F, 1.5F, 0.5F);
			poseStack.mulPose(Axis.XP.rotationDegrees(-180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, this.vibraniumShieldItemModel.renderType(VIBRANIUM_SHIELD_LOCATION), false, stack.hasFoil());
			this.vibraniumShieldItemModel.renderToBuffer(poseStack, consumer, packedLight, overlay);
			poseStack.popPose();
		}
	}
}