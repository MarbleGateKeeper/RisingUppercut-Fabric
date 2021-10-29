package love.marblegate.risinguppercut.registry;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.entity.RisingUppercutWatcher;
import love.marblegate.risinguppercut.entity.RocketPunchImpactWatcher;
import love.marblegate.risinguppercut.entity.RocketPunchProcessWatcher;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static EntityType<RocketPunchProcessWatcher> ROCKET_PUNCH_PROCESS_WATCHER;
    public static EntityType<RocketPunchImpactWatcher> ROCKET_PUNCH_IMPACT_WATCHER;
    public static EntityType<RisingUppercutWatcher> RISING_UPPERCUT_WATCHER;

    public static void ini(){
        ROCKET_PUNCH_PROCESS_WATCHER = Registry.register(Registry.ENTITY_TYPE, new Identifier(RisingUppercut.MODID,"rocket_punch_process_watcher"), FabricEntityTypeBuilder.<RocketPunchProcessWatcher>create(SpawnGroup.MISC, RocketPunchProcessWatcher::new).build());
        ROCKET_PUNCH_IMPACT_WATCHER = Registry.register(Registry.ENTITY_TYPE, new Identifier(RisingUppercut.MODID,"rocket_punch_impact_watcher"), FabricEntityTypeBuilder.<RocketPunchImpactWatcher>create(SpawnGroup.MISC, RocketPunchImpactWatcher::new).build());
        RISING_UPPERCUT_WATCHER = Registry.register(Registry.ENTITY_TYPE, new Identifier(RisingUppercut.MODID,"rising_uppercut_watcher"), FabricEntityTypeBuilder.<RisingUppercutWatcher>create(SpawnGroup.MISC, RisingUppercutWatcher::new).build());
    }
}
