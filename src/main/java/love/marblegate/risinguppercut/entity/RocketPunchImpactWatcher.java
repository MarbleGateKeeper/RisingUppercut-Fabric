package love.marblegate.risinguppercut.entity;

import love.marblegate.risinguppercut.registry.EntityRegistry;
import love.marblegate.risinguppercut.util.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class RocketPunchImpactWatcher extends Entity {
    static final TrackedData<Integer> TIMER = DataTracker.registerData(RocketPunchImpactWatcher.class, TrackedDataHandlerRegistry.INTEGER);
    int effectiveChargeTime;
    double knockbackSpeedIndex;
    float damagePerEffectiveCharge;
    double dx;
    double dz;
    boolean ignoreArmor;
    boolean healing;
    boolean isFireDamage;
    PlayerEntity source;
    List<YUnchangedLivingEntity> watchedEntities;


    public RocketPunchImpactWatcher(EntityType<? extends RocketPunchImpactWatcher> entityTypeIn, World world) {
        super(entityTypeIn, world);
    }

    public RocketPunchImpactWatcher(World world, BlockPos pos, int timer, int effectiveChargeTime, double knockbackSpeedIndex, float damagePerEffectiveCharge, double dx, double dz, boolean ignoreArmor, boolean healing, boolean isFireDamage, PlayerEntity source) {
        super(EntityRegistry.ROCKET_PUNCH_IMPACT_WATCHER, world);
        setPos(pos.getX(), pos.getY(), pos.getZ());
        dataTracker.set(TIMER, timer);
        this.effectiveChargeTime = effectiveChargeTime;
        this.knockbackSpeedIndex = knockbackSpeedIndex;
        this.damagePerEffectiveCharge = damagePerEffectiveCharge;
        this.dx = dx;
        this.dz = dz;
        this.source = source;
        this.isFireDamage = isFireDamage;
        this.healing = healing;
        this.ignoreArmor = ignoreArmor;
        watchedEntities = new ArrayList<>();
    }

    public void watch(LivingEntity livingEntity) {
        if (livingEntity != null) {
            watchedEntities.add(new YUnchangedLivingEntity(livingEntity));
        }
    }

    public void removeFromWatchList(YUnchangedLivingEntity yUnchangedLivingEntity) {
        if (yUnchangedLivingEntity != null) {
            watchedEntities.remove(yUnchangedLivingEntity);
        }
    }


    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(TIMER, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient()) {
            int temp = dataTracker.get(TIMER);
            if (watchedEntities != null && source != null) {
                if (!watchedEntities.isEmpty()) {
                    List<YUnchangedLivingEntity> entitiesRemoveFromWatchList = new ArrayList<>();
                    for (YUnchangedLivingEntity entity : watchedEntities) {
                        if (entity.livingEntity.horizontalCollision) {
                            DamageSource damageSource = ModDamageSource.impactWall(source);
                            float realDamageApplied = damagePerEffectiveCharge * effectiveChargeTime + 1;
                            if (effectiveChargeTime - temp < 10) realDamageApplied = realDamageApplied * 2 - 1;
                            {
                                if (ignoreArmor) {
                                    damageSource.bypassesArmor();
                                    entity.livingEntity.damage(damageSource, realDamageApplied);
                                } else if (isFireDamage) {
                                    damageSource.isFire();
                                    entity.livingEntity.damage(damageSource, realDamageApplied);
                                } else if (healing) {
                                    entity.livingEntity.heal(damagePerEffectiveCharge);
                                } else {
                                    entity.livingEntity.damage(damageSource, realDamageApplied);
                                }
                            }
                            entitiesRemoveFromWatchList.add(entity);
                        } else {
                            entity.setVelocity(dx * knockbackSpeedIndex, dz * knockbackSpeedIndex);
                        }
                    }
                    for (YUnchangedLivingEntity remove : entitiesRemoveFromWatchList) {
                        removeFromWatchList(remove);
                    }
                    if (temp - 1 == 0) {
                        watchedEntities.clear();
                        remove(RemovalReason.DISCARDED);
                    } else dataTracker.set(TIMER, temp - 1);
                } else {
                    if (temp - 1 == 0) remove(RemovalReason.DISCARDED);
                    else dataTracker.set(TIMER, temp - 1);
                }
            } else {
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        source = null;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }


    static class YUnchangedLivingEntity {
        LivingEntity livingEntity;
        double Y;

        public YUnchangedLivingEntity(LivingEntity livingEntity) {
            this.livingEntity = livingEntity;
            Y = livingEntity.getY();
        }

        void setVelocity(double X, double Z) {
            livingEntity.setVelocity(X, 0, Z);
            livingEntity.setPos(livingEntity.getX(), Y, livingEntity.getZ());
            livingEntity.velocityModified = true;
        }

    }
}
