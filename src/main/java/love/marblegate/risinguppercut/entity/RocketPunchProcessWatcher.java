package love.marblegate.risinguppercut.entity;


import love.marblegate.risinguppercut.registry.EntityRegistry;
import love.marblegate.risinguppercut.util.LootUtil;
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
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class RocketPunchProcessWatcher extends Entity {
    static final TrackedData<Integer> TIMER = DataTracker.registerData(RocketPunchProcessWatcher.class, TrackedDataHandlerRegistry.INTEGER);
    int effectiveChargeTime;
    double dx;
    double dz;
    float damagePerEffectiveCharge;
    double speedIndex;
    double knockbackSpeedIndex;
    boolean ignoreArmor;
    boolean healing;
    boolean isFireDamage;
    int shouldLoot;
    PlayerEntity source;
    boolean stopTracking = false;


    public RocketPunchProcessWatcher(EntityType<? extends RocketPunchProcessWatcher> entityTypeIn, World world) {
        super(entityTypeIn, world);
    }

    public RocketPunchProcessWatcher(World world, BlockPos pos, int effectiveChargeTime, double knockbackSpeedIndex, double speedIndex, float damagePerEffectiveCharge, double dx, double dz, boolean ignoreArmor, boolean healing, boolean isFireDamage, PlayerEntity source, int shouldLoot) {
        super(EntityRegistry.ROCKET_PUNCH_PROCESS_WATCHER, world);
        setPos(pos.getX(), pos.getY(), pos.getZ());
        dataTracker.set(TIMER, effectiveChargeTime);
        this.effectiveChargeTime = effectiveChargeTime;
        this.knockbackSpeedIndex = knockbackSpeedIndex;
        this.damagePerEffectiveCharge = damagePerEffectiveCharge;
        this.dx = dx;
        this.dz = dz;
        this.source = source;
        this.isFireDamage = isFireDamage;
        this.healing = healing;
        this.ignoreArmor = ignoreArmor;
        this.shouldLoot = shouldLoot;
        this.speedIndex = speedIndex;
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

            //Deal with rocket punch is valid
            if (temp > 0 && !stopTracking && source != null) {
                //Slightly enlarge player's hitbox
                Box collideBox = source.getBoundingBox().expand(0.5f, 0, 0.5f);

                //Collision Detection
                List<LivingEntity> checks = world.getNonSpectatingEntities(LivingEntity.class, collideBox);
                checks.remove(source);

                //If any mob is detected
                if (!checks.isEmpty()) {
                    // spawn an watchEntity to simulate rocket punch effect
                    RocketPunchImpactWatcher watchEntity = new RocketPunchImpactWatcher(world, source.getBlockPos(), temp, effectiveChargeTime,
                            knockbackSpeedIndex, damagePerEffectiveCharge, dx, dz,
                            ignoreArmor, healing, isFireDamage, source);
                    for (LivingEntity target : checks) {
                        // Deal damage
                        DamageSource damageSource = ModDamageSource.punch(source);
                        if (shouldLoot > 0) {
                            LootUtil.dropLoot(target, DamageSource.player(source), true, source);
                        }
                        if (isFireDamage) {
                            damageSource.isFire();
                            target.setOnFireFor(3);
                            target.damage(damageSource, damagePerEffectiveCharge * effectiveChargeTime);
                        } else if (ignoreArmor) {
                            damageSource.bypassesArmor();
                            target.damage(damageSource, damagePerEffectiveCharge * effectiveChargeTime);
                        } else if (healing) {
                            target.heal(damagePerEffectiveCharge * effectiveChargeTime);
                        } else {
                            target.damage(damageSource, damagePerEffectiveCharge * effectiveChargeTime);
                        }

                        if (target.isAlive()) {
                            watchEntity.watch(target);
                        }
                    }
                    source.world.spawnEntity(watchEntity);

                    // Player stop moving and clear pocket punch status
                    source.setVelocity(0, 0, 0);
                    source.velocityModified = true;
                    stopTracking = true;
                }

                // If rocket punch is active and player hit a wall
                // stop player and clear rocket punch status
                if (source.horizontalCollision && !stopTracking) {
                    source.setVelocity(0, 0, 0);
                    source.velocityModified = true;
                    stopTracking = true;
                }

                // Deal with player rocket punch movement
                if (!stopTracking) {
                    // lock moving direction
                    source.setVelocity(dx * speedIndex, 0.1, dz * speedIndex);
                    source.velocityModified = true;
                    dataTracker.set(TIMER, temp - 1);
                }
            }

            if (stopTracking || source == null || temp == 0) {
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


}
