package love.marblegate.risinguppercut.fabricasm;

import love.marblegate.risinguppercut.item.Gauntlet;
import love.marblegate.risinguppercut.mixin.AccessEnchantmentTarget;
import net.minecraft.item.Item;

public class GauntletEnchantmentTarget extends AccessEnchantmentTarget {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof Gauntlet;
    }
}
