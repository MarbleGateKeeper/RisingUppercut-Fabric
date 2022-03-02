package love.marblegate.risinguppercut.fabricasm;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable{
    @Override
    public void run() {
        MappingResolver mapResolver = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = mapResolver.mapClassName("intermediary", "net.minecraft.class_1886");
        EnumAdder enchantmentTargetAdder = ClassTinkerers.enumBuilder(enchantmentTarget);
        enchantmentTargetAdder.addEnumSubclass("GAUNTLET", "love.marblegate.risinguppercut.fabricasm.GauntletEnchantmentTarget");
        enchantmentTargetAdder.build();
    }
}
