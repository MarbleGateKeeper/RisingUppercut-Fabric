package love.marblegate.risinguppercut.registry;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.statuseffect.SafeLanding;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffectRegistry {
    public static final StatusEffect SAFE_LANDING = new SafeLanding();

    public static void ini(){
        register(SAFE_LANDING,"safe_landing");
    }

    private static void register(StatusEffect effect, String id){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(RisingUppercut.MODID,id),effect);
    }
}
