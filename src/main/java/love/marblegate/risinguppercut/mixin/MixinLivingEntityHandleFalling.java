package love.marblegate.risinguppercut.mixin;

import love.marblegate.risinguppercut.event.LivingEntityHandleFallCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntityHandleFalling {

    @Inject(at = @At("HEAD"), method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", cancellable = true)
    private void handleFalling(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        ActionResult result = LivingEntityHandleFallCallback.EVENT.invoker().handle((LivingEntity) (Object) this,fallDistance,damageMultiplier);
        if(result == ActionResult.FAIL) {
            info.setReturnValue(false);
        }
        else if(result == ActionResult.SUCCESS) {
            info.setReturnValue(true);
        }
    }
}
