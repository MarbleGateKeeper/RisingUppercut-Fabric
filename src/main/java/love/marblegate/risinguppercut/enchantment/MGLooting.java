package love.marblegate.risinguppercut.enchantment;

import love.marblegate.risinguppercut.registry.EnchantmentRegistry;
import love.marblegate.risinguppercut.util.ModEnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class MGLooting extends Enchantment {
    public MGLooting() {
        super(Rarity.VERY_RARE, ModEnchantmentTarget.GAUNTLET, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canAccept(Enchantment p_77326_1_) {
        return super.canAccept(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.GUARDIAN_ANGEL;
    }

    @Override
    public int getMinPower(int level) {
        return 25 + level * 5;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 30;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

}
