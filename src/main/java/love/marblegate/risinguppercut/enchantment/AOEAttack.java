package love.marblegate.risinguppercut.enchantment;

import love.marblegate.risinguppercut.util.ModEnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class AOEAttack extends Enchantment {
    public AOEAttack() {
        super(Rarity.VERY_RARE, ModEnchantmentTarget.GAUNTLET, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
