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
	private final ModelPart rotation_control;

	public VibraniumShieldModel(ModelPart root) {
		this.root = root.getChild("root");
		this.rotation_control = this.root.getChild("rotation_control");
	}

	@Override
	public void setupAnim(VibraniumShield entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTicks = ageInTicks - entity.tickCount;
		float yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot()) * Mth.DEG_TO_RAD;
		float xRot = Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot()) * Mth.DEG_TO_RAD;
		this.animate(entity.projectileAnimationState, VibraniumShieldAnimations.SPIN, ageInTicks);
		this.rotation_control.yRot = yRot;
		this.rotation_control.xRot = xRot;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition rotation_control = root.addOrReplaceChild("rotation_control", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));
		PartDefinition shield = rotation_control.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -0.5F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        shield.addOrReplaceChild("handles", CubeListBuilder.create().texOffs(0, 17).addBox(4.0F, -6.0F, -1.99F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).mirror().addBox(-6.0F, -6.0F, -1.99F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 1.5F));
        return LayerDefinition.create(meshdefinition, 64, 64);
	}
}