package love.marblegate.risinguppercut.enchantment;

import love.marblegate.risinguppercut.registry.EnchantmentRegistry;
import love.marblegate.risinguppercut.util.ModEnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class RocketPunchCoolingAssist extends Enchantment {
    public RocketPunchCoolingAssist() {
        super(Rarity.RARE, ModEnchantmentTarget.GAUNTLET, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canAccept(Enchantment p_77326_1_) {
        return super.canAccept(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.RISING_UPPERCUT_COOLING_ASSIST;
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxPower(int level) {
        return 60;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

}
