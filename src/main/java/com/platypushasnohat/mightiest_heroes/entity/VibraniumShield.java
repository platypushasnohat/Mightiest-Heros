package com.platypushasnohat.mightiest_heroes.entity;

import com.platypushasnohat.mightiest_heroes.registry.HeroEntities;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class VibraniumShield extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> RETURN_TIME = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLOT = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.INT);

    public AnimationState projectileAnimationState = new AnimationState();

    public VibraniumShield(EntityType<? extends VibraniumShield> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public VibraniumShield(Level level, double x, double y, double z) {
        super(HeroEntities.VIBRANIUM_SHIELD.get(), x, y, z, level);
        this.noPhysics = true;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, 1.0F);
        builder.define(RETURN_TIME, 0);
        builder.define(SLOT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getDamage());
        compoundTag.putInt("Slot", this.getSlot());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDamage(compoundTag.getInt("Damage"));
        this.setSlot(compoundTag.getInt("Slot"));
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public int getReturnTime() {
        return this.entityData.get(RETURN_TIME);
    }

    public void setReturnTime(int returnTime) {
        this.entityData.set(RETURN_TIME, returnTime);
    }

    public int getSlot() {
        return this.entityData.get(SLOT);
    }

    public void setSlot(int slot) {
        this.entityData.set(SLOT, slot);
    }

    @Override
    protected Item getDefaultItem() {
        return HeroItems.VIBRANIUM_SHIELD.get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4.0F;
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public void push(double x, double y, double z) {
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (!this.level().isClientSide && this.getOwner() instanceof LivingEntity living) {
            DamageSource damageSource = this.damageSources().mobProjectile(this, living);
            if (entity.hurt(damageSource, this.getDamage())) {
                this.playImpactSound(entity.getX(), entity.getY(), entity.getZ());
                this.flyBack(this.getOwner());
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        this.setReturnTime(0);
        this.playImpactSound(pos.getX(), pos.getY(), pos.getZ());
        if (this.getOwner() != null) {
            this.flyBack(this.getOwner());
        }
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 deltaMovement = this.getDeltaMovement();
        ItemStack stack = this.getItem();
        if (!this.level().isClientSide) {
            Entity owner = this.getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(this.level()) || this.distanceTo(owner) > 1000.0F) {
                this.discard();
                return;
            }

            if (owner instanceof LivingEntity living) {
                if (this.getReturnTime() > 0) {
                    this.setReturnTime(this.getReturnTime() - 1);
                }
                else {
                    float height = living.getBbHeight();
                    Vec3 ownerPos = living.position().add(0, height * 0.6F, 0);
                    double velocity = Mth.clamp(deltaMovement.length() * 3.0D, 0.5F, 1.5F);
                    Vec3 returnMotion = ownerPos.subtract(position()).normalize().scale(velocity);
                    this.setDeltaMovement(deltaMovement.lerp(returnMotion, 0.2F));
                    if (this.isAlive() && this.distanceTo(living) < 3.0F) {
                        if (living instanceof ServerPlayer player) {
                            player.getCooldowns().addCooldown(stack.getItem(), 30);
//                            DisabledItem.enableItem(player, this.getSlot());
                        }
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.NEUTRAL, 1.0F, SinewSoundUtils.randomizePitch(this.level()));
                        this.discard();
                    }
                }
            }
        } else {
            this.projectileAnimationState.animateWhen(this.isAlive(), this.tickCount);
        }
    }

    @Override
    protected void updateRotation() {
        ProjectileUtil.rotateTowardsMovement(this, 0.5F);
    }

    @Override
    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float inaccuracy) {
        float x = -Mth.sin(rotationYaw * Mth.DEG_TO_RAD) * Mth.cos(rotationPitch * Mth.DEG_TO_RAD);
        float y = -Mth.sin((rotationPitch + pitchOffset) * Mth.DEG_TO_RAD);
        float z = Mth.cos(rotationYaw * Mth.DEG_TO_RAD) * Mth.cos(rotationPitch * Mth.DEG_TO_RAD);
        this.shoot(x, y, z, velocity, inaccuracy);
        Vec3 shooterMovement = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(shooterMovement.x, 0.0F, shooterMovement.z));
    }

    private void playImpactSound(double x, double y, double z) {
        this.level().playSound(null, x, y, z, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.NEUTRAL, 1.0F, SinewSoundUtils.randomizePitch(this.level()));
    }

    public void flyBack(Entity owner) {
        Vec3 ownerPos = owner.position().add(0.0F, owner.getBbHeight() * 0.5F, 0.0F);
        Vec3 returnMotion = ownerPos.subtract(this.position()).normalize().scale(0.8F);
        this.setDeltaMovement(returnMotion);
    }
}