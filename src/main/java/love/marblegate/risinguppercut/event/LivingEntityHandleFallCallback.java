package love.marblegate.risinguppercut.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

/**
 * Callback for LivingEntity fall and is about to take damage.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing and force this fall to happen.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and cancel this fall.</ul>
 *
 */
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
