package love.marblegate.risinguppercut.event;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.registry.StatusEffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class Event {

    public static void ini(){
        LivingEntityHandleFallCallback.EVENT.register((livingEntity, fallDistance, damageMultiplier) ->
        {
            boolean isCancelled = false;
            if (!livingEntity.world.isClient() && livingEntity instanceof PlayerEntity) {
                    if (((PlayerEntity) livingEntity).hasStatusEffect(StatusEffectRegistry.SAFE_LANDING)) {
                        isCancelled = true;
                        if (RisingUppercut.CONFIG.safeLandingConfig.disposableEffect) {
                            ((PlayerEntity) livingEntity).removeStatusEffect(StatusEffectRegistry.SAFE_LANDING);
                            //Sync to client
                            /*Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayer) event.getEntityLiving()
                                    ),
                                    new RemoveEffectSyncToClientPacket(MobEffectRegistry.SAFE_LANDING.get()));*/
                        }
                }
            }
            if(isCancelled) return ActionResult.FAIL;
            else return ActionResult.PASS;
        });
    }
}
