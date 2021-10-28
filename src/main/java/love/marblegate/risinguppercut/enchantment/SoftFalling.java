package love.marblegate.risinguppercut.enchantment;

import love.marblegate.risinguppercut.util.ModEnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class SoftFalling extends Enchantment {
    public SoftFalling() {
        super(Rarity.VERY_RARE, ModEnchantmentTarget.GAUNTLET, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMinPower(int p_77321_1_) {
        return 30;
    }

    @Override
    public int getMaxPower(int p_223551_1_) {
        return 60;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

}
