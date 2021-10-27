package love.marblegate.risinguppercut.item;

import love.marblegate.risinguppercut.util.ModGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class Gauntlet extends Item {
    public Gauntlet() {
        super(new FabricItemSettings().maxCount(1).maxDamage(1024).fireproof().group(ModGroup.GENERAL));
    }
}
