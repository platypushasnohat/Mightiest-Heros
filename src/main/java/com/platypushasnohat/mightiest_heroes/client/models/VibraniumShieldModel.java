package com.platypushasnohat.mightiest_heroes.client.models;

import com.platypushasnohat.mightiest_heroes.client.models.animations.VibraniumShieldAnimations;
import com.platypushasnohat.mightiest_heroes.entity.VibraniumShield;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class VibraniumShieldModel extends HierarchicalModel<VibraniumShield> {

	private final ModelPart root;
	private final ModelPart handles;

	public VibraniumShieldModel(ModelPart root) {
		this.root = root.getChild("root");
		ModelPart shield = this.root.getChild("shield");
		this.handles = shield.getChild("handles");
	}

	@Override
	public void setupAnim(VibraniumShield entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTicks = ageInTicks - entity.tickCount;
		this.animate(entity.projectileAnimationState, VibraniumShieldAnimations.SPIN, ageInTicks);
		float yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot()) * Mth.DEG_TO_RAD;
		this.root.xRot += entity.getPitch(partialTicks) * Mth.DEG_TO_RAD;
		this.root.yRot += yRot;
		this.root.zRot += -entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
		this.handles.y -= 0.99F;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shield = root.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -0.5F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        shield.addOrReplaceChild("handles", CubeListBuilder.create().texOffs(9, 49).addBox(2.0F, 0.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(9, 49).mirror().addBox(-4.0F, 0.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}
}