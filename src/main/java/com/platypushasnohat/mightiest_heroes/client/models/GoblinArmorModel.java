package com.platypushasnohat.mightiest_heroes.client.models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class GoblinArmorModel extends HumanoidModel<LivingEntity> {

    public static final GoblinArmorModel INSTANCE = new GoblinArmorModel(createBodyLayer().bakeRoot());

    private final ModelPart helmet1;
    private final ModelPart helmet2;
    private final ModelPart helmet3;
    private final ModelPart helmet4;

    public GoblinArmorModel(ModelPart root) {
        super(root);
        ModelPart head = root.getChild("head");
        this.helmet1 = head.getChild("helmet1");
        this.helmet2 = this.helmet1.getChild("helmet2");
        this.helmet3 = this.helmet2.getChild("helmet3");
        this.helmet4 = this.helmet3.getChild("helmet4");
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

        PartDefinition helmet1 = head.addOrReplaceChild("helmet1", CubeListBuilder.create().texOffs(64, 64).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(116, 83).addBox(-2.0F, -8.0F, -4.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.26F))
                .texOffs(82, 112).addBox(-1.0F, -5.0F, -7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        helmet1.addOrReplaceChild("Right ear_r1", CubeListBuilder.create().texOffs(80, 112).mirror().addBox(0.0F, -6.0F, 0.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.25F, -3.75F, -1.25F, 0.0F, -0.3927F, 0.0F));

        helmet1.addOrReplaceChild("Left ear_r1", CubeListBuilder.create().texOffs(80, 112).addBox(0.0F, -6.0F, 0.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, -3.75F, -1.25F, 0.0F, 0.3927F, 0.0F));

        PartDefinition helmet2 = helmet1.addOrReplaceChild("helmet2", CubeListBuilder.create().texOffs(64, 80).addBox(-3.0F, -6.0F, -2.0F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 1.4F));

        PartDefinition helmet3 = helmet2.addOrReplaceChild("helmet3", CubeListBuilder.create().texOffs(104, 96).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 6.0F));

        helmet3.addOrReplaceChild("helmet4", CubeListBuilder.create().texOffs(120, 98).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(92, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(64, 96).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        leftArm.addOrReplaceChild("left_pauldron", CubeListBuilder.create().texOffs(64, 112).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(88, 96).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

        rightArm.addOrReplaceChild("right_pauldron", CubeListBuilder.create().texOffs(64, 112).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(88, 96).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)).mirror(false), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create().texOffs(112, 64).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(96, 64).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create().texOffs(112, 64).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(96, 64).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)).mirror(false), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
