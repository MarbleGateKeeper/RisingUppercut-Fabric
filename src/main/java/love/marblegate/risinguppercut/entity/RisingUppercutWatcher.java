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


public class RisingUppercutWatcher extends Entity {
    static final TrackedData<Integer> TIMER = DataTracker.registerData(RisingUppercutWatcher.class, TrackedDataHandlerRegistry.INTEGER);
    int totalTime;
    int floatingTime;
    double speedIndex;
    float damage;
    boolean ignoreArmor;
    boolean healing;
    boolean isFireDamage;
    PlayerEntity source; //Can be null
    List<LivingEntity> watchedEntities;

    public RisingUppercutWatcher(World world, BlockPos pos, PlayerEntity source, int upwardTime, int floatingTime, double speedIndex, float damage, boolean ignoreArmor, boolean healing, boolean isFireDamage) {
        super(EntityRegistry.RISING_UPPERCUT_WATCHER, world);
        setPos(pos.getX(), pos.getY(), pos.getZ());
        dataTracker.set(TIMER, upwardTime + floatingTime);
        totalTime = upwardTime + floatingTime;
        this.source = source;
        this.floatingTime = floatingTime;
        this.speedIndex = speedIndex;
        this.damage = damage;
        this.ignoreArmor = ignoreArmor;
        this.healing = healing;
        this.isFireDamage = isFireDamage;
        watchedEntities = new ArrayList<>();
    }

    public RisingUppercutWatcher(EntityType<? extends RisingUppercutWatcher> entityTypeIn, World world) {
        super(entityTypeIn, world);
    }

    public void watch(LivingEntity livingEntity) {
        if (livingEntity != null) {
            watchedEntities.add(livingEntity);
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
                    for (LivingEntity entity : watchedEntities) {
                        if (temp == totalTime - 1) {
                            DamageSource damageSource = ModDamageSource.uppercut(source);
                            if (isFireDamage) {
                                damageSource.isFire();
                                entity.damage(damageSource, damage);
                                entity.setOnFireFor(3);
                            } else if (ignoreArmor) {
                                damageSource.bypassesArmor();
                                entity.damage(damageSource, damage);
                            } else if (healing) {
                                System.out.print(entity.getHealth() + "->");
                                entity.heal(damage);
                                System.out.print(entity.getHealth() + "\n");
                            } else {
                                entity.damage(damageSource, damage);
                            }
                        }
                        if (temp > floatingTime) {
                            moveVertically(entity, temp * speedIndex);
                        } else if (temp > floatingTime / 2) {
                            moveVertically(entity, 0.1);
                        } else {
                            moveVertically(entity, -0.1);
                        }
                    }
                }
                if (temp > floatingTime) {
                    moveVertically(source, temp * speedIndex);
                } else if (temp > floatingTime / 2) {
                    moveVerticallyWithHorizonControl(source, 0.1);
                } else {
                    moveVerticallyWithHorizonControl(source, -0.1);
                }
                if (temp - 1 == 0) remove(RemovalReason.DISCARDED);
                else dataTracker.set(TIMER, temp - 1);
            } else {
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        source = null;
        dataTracker.set(TIMER, 0);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    void moveVertically(LivingEntity livingEntity, double speed) {
        livingEntity.setVelocity(0, speed, 0);
        livingEntity.velocityModified = true;
    }

    void moveVerticallyWithHorizonControl(LivingEntity livingEntity, double speed) {
        livingEntity.setVelocity(livingEntity.getVelocity().x, speed, livingEntity.getVelocity().z);
        livingEntity.velocityModified = true;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

}
