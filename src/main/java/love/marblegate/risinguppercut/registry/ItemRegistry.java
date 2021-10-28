package love.marblegate.risinguppercut.registry;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.item.Gauntlet;
import love.marblegate.risinguppercut.util.ModGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final Item GAUNTLET = new Gauntlet();
    public static final Item GAUNTLET_SURFACE = new Item(new FabricItemSettings().group(ModGroup.GENERAL).maxCount(1));
    public static final Item GAUNTLET_KINETIC_CORE = new Item(new FabricItemSettings().group(ModGroup.GENERAL).maxCount(1));
    public static final Item GAUNTLET_COORDINATE_DRIVE = new Item(new FabricItemSettings().group(ModGroup.GENERAL).maxCount(1));
    public static final Item GAUNTLET_COOLER = new Item(new FabricItemSettings().group(ModGroup.GENERAL).maxCount(1));

    public static void ini(){
        register(GAUNTLET,"gauntlet");
        register(GAUNTLET_SURFACE,"gauntlet_surface");
        register(GAUNTLET_KINETIC_CORE,"gauntlet_kinetic_core");
        register(GAUNTLET_COORDINATE_DRIVE,"gauntlet_coordinate_drive");
        register(GAUNTLET_COOLER,"gauntlet_cooler");
    }

    private static void register(Item item, String id){
        Registry.register(Registry.ITEM, new Identifier(RisingUppercut.MODID,id),item);
    }
}
