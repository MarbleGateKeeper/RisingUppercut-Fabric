package love.marblegate.risinguppercut.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface LivingEntityHandleFallCallback {

    Event<LivingEntityHandleFallCallback> EVENT = EventFactory.createArrayBacked(LivingEntityHandleFallCallback.class,
            (listeners) -> (livingEntity, fallDistance, damageMultiplier) -> {
                for (LivingEntityHandleFallCallback listener : listeners) {
                    ActionResult result = listener.handle(livingEntity, fallDistance, damageMultiplier);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult handle(LivingEntity livingEntity, float fallDistance, float damageMultiplier);
}
