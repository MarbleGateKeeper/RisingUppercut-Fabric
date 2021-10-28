package love.marblegate.risinguppercut.registry;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentRegistry {
    public static final Enchantment MARBLEGATE_LOOTING = new MGLooting();
    public static final Enchantment AOE_ATTACK = new AOEAttack();
    public static final Enchantment DRAGONBITE = new Dragonbite();
    public static final Enchantment FLAMEBURST = new FlameBurst();
    public static final Enchantment GUARDIAN_ANGEL = new GuardianAngel();
    public static final Enchantment SOFTFALLING = new SoftFalling();
    public static final Enchantment MARBLEGATE_KINETIC_OPTIMIZATION = new MGKineticOptimization();
    public static final Enchantment KADOKAWA_KINETIC_OPTIMIZATION = new KKineticOptimization();
    public static final Enchantment RISING_UPPERCUT_CALCULATION_ASSIST = new RisingUppercutCalculationAssist();
    public static final Enchantment ROCKET_PUNCH_CALCULATION_ASSIST = new RocketPunchCalculationAssist();
    public static final Enchantment RISING_UPPERCUT_COOLING_ASSIST = new RisingUppercutCoolingAssist();
    public static final Enchantment ROCKET_PUNCH_COOLING_ASSIST = new RocketPunchCoolingAssist();

    public static void ini() {
        register(MARBLEGATE_LOOTING, "marblegate_looting");
        register(AOE_ATTACK, "aoe_attack");
        register(DRAGONBITE, "dragonbite");
        register(FLAMEBURST, "flameburst");
        register(GUARDIAN_ANGEL, "guardian_angel");
        register(SOFTFALLING, "softfalling");
        register(MARBLEGATE_KINETIC_OPTIMIZATION, "marblegate_kinetic_optimization");
        register(KADOKAWA_KINETIC_OPTIMIZATION, "kadokawa_kinetic_optimization");
        register(RISING_UPPERCUT_CALCULATION_ASSIST, "rising_uppercut_calculation_assist");
        register(ROCKET_PUNCH_CALCULATION_ASSIST, "rocket_punch_calculation_assist");
        register(RISING_UPPERCUT_COOLING_ASSIST, "rising_uppercut_cooling_assist");
        register(ROCKET_PUNCH_COOLING_ASSIST, "rocket_punch_cooling_assist");
    }

    private static void register(Enchantment enchantment, String id){
        Registry.register(Registry.ENCHANTMENT, new Identifier(RisingUppercut.MODID,id),enchantment);
    }
}
