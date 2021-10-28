package love.marblegate.risinguppercut.item;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.config.Configuration;
import love.marblegate.risinguppercut.registry.EnchantmentRegistry;
import love.marblegate.risinguppercut.util.ModGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

import java.util.Map;

public class Gauntlet extends Item {
    public Gauntlet() {
        super(new FabricItemSettings().maxCount(1).maxDamage(1024).fireproof().group(ModGroup.GENERAL));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 18;
    }

    static class SkillData {

        public static int getRocketPunchMaxChangeTime(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.KADOKAWA_KINETIC_OPTIMIZATION))
                return getConfig().rocketPunchConfig.maxChargeTime + 4;
            else if (isItemEnchanted(itemStack, EnchantmentRegistry.MARBLEGATE_KINETIC_OPTIMIZATION))
                return getConfig().rocketPunchConfig.maxChargeTime + 12;
            return getConfig().rocketPunchConfig.maxChargeTime;
        }

        public static float getRocketPunchDamagePerTick(ItemStack itemStack) {
            //No Enchantment Modifying This.
            return (float) getConfig().rocketPunchConfig.damage;
        }

        public static double getRocketPunchSpeedIndex(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.ROCKET_PUNCH_CALCULATION_ASSIST))
                return getConfig().rocketPunchConfig.movementSpeedIndex * (1 + 0.3);
            return getConfig().rocketPunchConfig.movementSpeedIndex;
        }

        public static double getRocketPunchKnockbackSpeedIndex(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.KADOKAWA_KINETIC_OPTIMIZATION))
                return getConfig().rocketPunchConfig.knockbackSpeedIndex * (1 + 0.5);
            return getConfig().rocketPunchConfig.knockbackSpeedIndex;
        }

        public static int getRocketPunchCooldown(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.ROCKET_PUNCH_COOLING_ASSIST))
                return getConfig().rocketPunchConfig.cooldown - 20;
            return getConfig().rocketPunchConfig.cooldown;
        }

        public static int getRisingUppercutUpwardTime(ItemStack itemStack) {
            //No Enchantment Modifying This.
            return getConfig().risingUppercutConfig.uprisingTime;
        }

        public static int getRisingUppercutFloatingTime(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.MARBLEGATE_KINETIC_OPTIMIZATION))
                return getConfig().risingUppercutConfig.floatingTime + 8;
            return getConfig().risingUppercutConfig.floatingTime;
        }

        public static float getRisingUppercutDamage(ItemStack itemStack) {
            //Nothing Modifying This.
            return (float) getConfig().risingUppercutConfig.damage;
        }

        public static double getRisingUppercutSpeedIndex(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.RISING_UPPERCUT_CALCULATION_ASSIST))
                return getConfig().risingUppercutConfig.risingSpeedIndex * (1 + 0.3);
            return getConfig().risingUppercutConfig.risingSpeedIndex;
        }

        public static int getRisingUppercutCooldown(ItemStack itemStack) {
            if (isItemEnchanted(itemStack, EnchantmentRegistry.RISING_UPPERCUT_COOLING_ASSIST))
                return getConfig().risingUppercutConfig.cooldown - 20;
            return getConfig().risingUppercutConfig.cooldown;
        }

        public static boolean shouldRisingUppercutAOE(ItemStack itemStack) {
            return isItemEnchanted(itemStack, EnchantmentRegistry.AOE_ATTACK);
        }

        public static boolean shouldIgnoreArmor(ItemStack itemStack) {
            return isItemEnchanted(itemStack, EnchantmentRegistry.DRAGONBITE);
        }

        public static boolean shouldHeal(ItemStack itemStack) {
            return isItemEnchanted(itemStack, EnchantmentRegistry.GUARDIAN_ANGEL);
        }

        public static boolean shouldBeFireDamage(ItemStack itemStack) {
            return isItemEnchanted(itemStack, EnchantmentRegistry.FLAMEBURST);
        }

        public static int shouldLoot(ItemStack itemStack) {
            return getItemEnchantedLevel(itemStack, EnchantmentRegistry.MARBLEGATE_LOOTING);
        }

        public static boolean shouldApplySoftLanding(ItemStack itemStack) {
            if (getConfig().safeLandingConfig.doNotRequireEnchantment) return true;
            else return isItemEnchanted(itemStack, EnchantmentRegistry.SOFTFALLING);
        }

        public static int getSoftLandingDefaultDuration(ItemStack itemStack) {
            //Nothing Modifying This.
            return getConfig().safeLandingConfig.defaultDuration;
        }

        static boolean isItemEnchanted(ItemStack itemStack, Enchantment enchantment) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.get(itemStack);
            return enchantList.containsKey(enchantment);
        }

        static int getItemEnchantedLevel(ItemStack itemStack, Enchantment enchantment) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.get(itemStack);
            return enchantList.getOrDefault(enchantment, 0);
        }

        static Configuration getConfig(){
            return RisingUppercut.CONFIG;
        }
    }
}
