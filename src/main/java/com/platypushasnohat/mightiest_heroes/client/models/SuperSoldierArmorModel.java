package com.platypushasnohat.mightiest_heroes.client.models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class SuperSoldierArmorModel extends HumanoidModel<LivingEntity> {

    public static final SuperSoldierArmorModel INSTANCE = new SuperSoldierArmorModel(createBodyLayer().bakeRoot());

    public SuperSoldierArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition head = root.getChild("head");
        PartDefinition body = root.getChild("body");
        PartDefinition leftLeg = root.getChild("left_leg");
        PartDefinition rightLeg = root.getChild("right_leg");
        PartDefinition leftArm = root.getChild("left_arm");
        PartDefinition rightArm = root.getChild("right_arm");

        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(64, 64).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

        helmet.addOrReplaceChild("Right wing_r1", CubeListBuilder.create().texOffs(99, 90).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.25F, -6.0F, 1.0F, 0.0F, -0.1309F, 0.0F));

        helmet.addOrReplaceChild("Left wing_r1", CubeListBuilder.create().texOffs(99, 90).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, -6.0F, 1.0F, 0.0F, 0.1309F, 0.0F));

        body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(64, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.255F))
                .texOffs(88, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("legging_left", CubeListBuilder.create().texOffs(64, 96).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.261F))
                .texOffs(80, 96).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.246F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("legging_right", CubeListBuilder.create().texOffs(64, 96).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(80, 96).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.245F)).mirror(false), PartPose.ZERO);

        leftArm.addOrReplaceChild("shoulder_pad_left", CubeListBuilder.create().texOffs(96, 64).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(112, 64).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)), PartPose.ZERO);

        rightArm.addOrReplaceChild("shoulder_pad_right", CubeListBuilder.create().texOffs(96, 64).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(112, 64).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
