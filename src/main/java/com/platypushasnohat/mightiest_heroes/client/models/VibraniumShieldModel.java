package com.platypushasnohat.mightiest_heroes.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class VibraniumShieldModel extends Model {

	private final ModelPart root;

	public VibraniumShieldModel(ModelPart root) {
		super(RenderType::entityCutout);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -1.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        partdefinition.addOrReplaceChild("handles", CubeListBuilder.create().texOffs(0, 17).addBox(2.0F, -3.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).mirror().addBox(-4.0F, -3.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}