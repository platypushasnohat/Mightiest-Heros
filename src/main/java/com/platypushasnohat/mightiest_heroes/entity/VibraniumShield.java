package com.platypushasnohat.mightiest_heroes.entity;

import com.platypushasnohat.mightiest_heroes.items.VibraniumShieldItem;
import com.platypushasnohat.mightiest_heroes.registry.HeroEntities;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import com.platypushasnohat.mightiest_heroes.registry.HeroSoundEvents;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

public class VibraniumShield extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> RETURN_TIME = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLOT = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RICOCHET_COUNT = SynchedEntityData.defineId(VibraniumShield.class, EntityDataSerializers.INT);

    private static final int MAX_BOUNCES = 3;

    public AnimationState projectileAnimationState = new AnimationState();

    public float prevRoll;
    public float roll;

    public float prevPitch;
    public float pitch;

    public VibraniumShield(EntityType<? extends VibraniumShield> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public VibraniumShield(Level level, LivingEntity shooter) {
        super(HeroEntities.VIBRANIUM_SHIELD.get(), shooter, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, 1.0F);
        builder.define(RETURN_TIME, 0);
        builder.define(SLOT, 0);
        builder.define(RICOCHET_COUNT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getDamage());
        compoundTag.putInt("Slot", this.getSlot());
        compoundTag.putInt("RicochetCount", this.getRicochetCount());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDamage(compoundTag.getInt("Damage"));
        this.setSlot(compoundTag.getInt("Slot"));
        this.setRicochetCount(compoundTag.getInt("RicochetCount"));
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

    public int getRicochetCount() {
        return this.entityData.get(RICOCHET_COUNT);
    }

    public void setRicochetCount(int ricochetCount) {
        this.entityData.set(RICOCHET_COUNT, ricochetCount);
    }

    public float getPitch(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevPitch, this.pitch);
    }

    public float getRoll(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevRoll, this.roll);
    }

    private void updatePitchAndRoll() {
        this.prevPitch = this.pitch;
        this.prevRoll = this.roll;
        float target = 0.0F;
        if (this.isAlive()) {
            double dx = this.getX() - this.xo;
            double dy = this.getY() - this.yo;
            double dz = this.getZ() - this.zo;
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            double speed = Math.sqrt(horizontal * horizontal + dy * dy);
            float speedFactor = (float) Mth.clamp((speed - 0.01D) / (0.05D - 0.01D), 0.0D, 1.0D);
            if (speedFactor > 0.0F) {
                float angle = (float) (-(Mth.atan2(dy, horizontal) * (180.0D / Math.PI)));
                target = Mth.clamp(angle, -85.0F, 85.0F) * speedFactor;
            }

            float turn = Mth.degreesDifference(this.getYRot(), this.yRotO);
            if (Math.abs(turn) > 1.0F) {
                if (Math.abs(this.roll) < 45.0F) {
                    this.roll -= Math.signum(turn);
                }
            } else if (this.roll != 0.0F) {
                float sign = Math.signum(this.roll);
                this.roll -= sign * 0.85F;
                if (this.roll * sign < 0.0F) {
                    this.roll = 0.0F;
                }
            }
        } else {
            this.roll = 0.0F;
        }
        this.pitch += (target - this.pitch) * 0.4F;
    }

    private void playImpactSound(double x, double y, double z) {
        this.level().playSound(null, x, y, z, HeroSoundEvents.VIBRANIUM_SHIELD_IMPACT, SoundSource.NEUTRAL, 1.0F, SinewSoundUtils.randomizePitch(this.level()));
    }

    public void flyBack(Entity owner) {
        this.setRicochetCount(MAX_BOUNCES * 2);
        this.setReturnTime(0);
        Vec3 ownerPos = owner.position().add(0.0F, owner.getBbHeight() * 0.5F, 0.0F);
        Vec3 returnMotion = ownerPos.subtract(this.position()).normalize().scale(0.8F);
        this.setDeltaMovement(returnMotion);
    }

    private void moveToOwner(LivingEntity living) {
        ItemStack stack = this.getItem();
        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 ownerPos = new Vec3(living.getX(), living.getEyeY() - 0.1F, living.getZ());
        double velocity = Mth.clamp(deltaMovement.length() * 3.0D, 0.75F, 1.5F);
        Vec3 returnMotion = ownerPos.subtract(this.position()).normalize().scale(velocity);
        this.setDeltaMovement(deltaMovement.lerp(returnMotion, 0.2F));
        if (this.isAlive() && this.distanceTo(living) <= 2.0F) {
            VibraniumShieldItem.catchShield(this, stack, living);
        }
    }

    private void spawnBlockParticles(BlockPos pos, BlockState state) {
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            if (this.level().isClientSide) {
                for (int i = 0; i < 5; i++) {
                    Vec3 deltaMovement = this.getDeltaMovement();
                    double posX = this.getX() + (this.getRandom().nextDouble() - 0.5D);
                    double posZ = this.getZ() + (this.getRandom().nextDouble() - 0.5D);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(pos), posX, this.getY() + 0.1D, posZ, deltaMovement.x * -4.0D, 1.5D, deltaMovement.z * -4.0D);
                }
            }
        }
    }

    private boolean canHitEntity(Entity owner, Entity target) {
        return owner != null
                && target != this
                && target != owner
                && !target.isAlliedTo(owner)
                && !owner.isAlliedTo(target)
                && !(target instanceof OwnableEntity ownable && ownable.getOwner() == owner)
                && target.getControllingPassenger() != owner;
    }

    private boolean hasLineOfSight(Entity entity) {
        if (entity.level() != this.level()) {
            return false;
        } else {
            Vec3 position = new Vec3(this.getX(), this.getEyeY(), this.getZ());
            Vec3 targetPosition = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            return !(targetPosition.distanceTo(position) > (double) 128.0F) && this.level().clip(new ClipContext(position, targetPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
        }
    }

    private void ricochet() {
        Entity owner = this.getOwner();
        AABB searchBox = this.getBoundingBox().inflate(12.0D, 4.0D, 12.0D);
        Entity ricochetTo = null;
        for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, searchBox)) {
            if (this.hasLineOfSight(living) && this.canHitEntity(owner, living) && living.distanceTo(this) > 3.0D) {
                ricochetTo = living;
                break;
            }
        }
        if (ricochetTo != null && this.getRicochetCount() < MAX_BOUNCES) {
            this.bounce(ricochetTo, owner);
        }
        if (this.getOwner() != null && (ricochetTo == null || this.getRicochetCount() >= MAX_BOUNCES)) {
            this.flyBack(this.getOwner());
        }
    }

    private void bounce(Entity ricochetTo, Entity owner) {
        if (owner != null) {
            this.setRicochetCount(this.getRicochetCount() + 1);
            this.setReturnTime(this.getReturnTime() + 10);
            Vec3 direction = ricochetTo.getEyePosition().subtract(this.position()).normalize().scale(0.9F);
            this.setDeltaMovement(direction);
        }
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
        return 2.0F;
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
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        if (!this.level().isClientSide && owner instanceof LivingEntity living && this.canHitEntity(living, target)) {
            DamageSource damageSource = this.damageSources().mobProjectile(this, living);
            if (target.hurt(damageSource, this.getDamage())) {
                this.playImpactSound(target.getX(), target.getY(), target.getZ());
                this.ricochet();
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        BlockState state = this.level().getBlockState(pos);
        if (this.getOwner() != null) {
            this.ricochet();
        }
        this.spawnBlockParticles(pos, state);
        this.playImpactSound(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void updateRotation() {
        ProjectileUtil.rotateTowardsMovement(this, 0.4F);
    }

    @Override
    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        float xPos = -Mth.sin(y * Mth.DEG_TO_RAD) * Mth.cos(x * Mth.DEG_TO_RAD);
        float yPos = -Mth.sin((x + z) * Mth.DEG_TO_RAD);
        float zPos = Mth.cos(y * Mth.DEG_TO_RAD) * Mth.cos(x * Mth.DEG_TO_RAD);
        this.shoot(xPos, yPos, zPos, velocity, inaccuracy);
        Vec3 knownMovement = shooter.getKnownMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(knownMovement.x, 0.0F, knownMovement.z));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            Entity owner = this.getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(this.level()) || this.distanceTo(owner) > 1000.0F) {
                this.discard();
                return;
            }
            if (owner instanceof LivingEntity living) {
                if (this.getReturnTime() > 0) {
                    this.setReturnTime(this.getReturnTime() - 1);
                } else {
                    this.moveToOwner(living);
                }
            }
        } else {
            this.updatePitchAndRoll();
            this.projectileAnimationState.animateWhen(this.isAlive(), this.tickCount);
        }
    }
}