package love.marblegate.risinguppercut.util;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModGroup {
    public static ItemGroup GENERAL = FabricItemGroupBuilder.build(
            new Identifier(RisingUppercut.MODID, "all"),
            () -> new ItemStack(ItemRegistry.GAUNTLET.asItem())).setEnchantments(ModEnchantmentTarget.GAUNTLET);

}
