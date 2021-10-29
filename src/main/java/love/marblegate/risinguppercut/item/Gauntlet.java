package love.marblegate.risinguppercut.item;

import love.marblegate.risinguppercut.RisingUppercut;
import love.marblegate.risinguppercut.config.Configuration;
import love.marblegate.risinguppercut.entity.RisingUppercutWatcher;
import love.marblegate.risinguppercut.entity.RocketPunchProcessWatcher;
import love.marblegate.risinguppercut.registry.EnchantmentRegistry;
import love.marblegate.risinguppercut.registry.StatusEffectRegistry;
import love.marblegate.risinguppercut.util.ModGroup;
import love.marblegate.risinguppercut.util.RotationUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class Gauntlet extends Item {
    public Gauntlet() {
        super(new FabricItemSettings().maxCount(1).maxDamage(1024).fireproof().group(ModGroup.GENERAL));
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient()) {
            final int capTimer = Math.min((getMaxUseTime(stack) - remainingUseTicks), SkillData.getRocketPunchMaxChangeTime(stack));
            RocketPunchProcessWatcher initializer = new RocketPunchProcessWatcher(user.world, user.getBlockPos(), capTimer,
                    SkillData.getRocketPunchKnockbackSpeedIndex(stack), SkillData.getRocketPunchSpeedIndex(stack), SkillData.getRocketPunchDamagePerTick(stack),
                    RotationUtil.getHorizentalLookVecX(user), RotationUtil.getHorizentalLookVecZ(user),
                    SkillData.shouldIgnoreArmor(stack), SkillData.shouldHeal(stack), SkillData.shouldBeFireDamage(stack),
                    (PlayerEntity) user, SkillData.shouldLoot(stack));
            world.spawnEntity(initializer);

            stack.damage(1, ((PlayerEntity) user), (entity) -> {
                entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
            ((PlayerEntity) user).getItemCooldownManager().set(this, SkillData.getRocketPunchCooldown(stack));
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target.world.isClient() || hand == Hand.OFF_HAND) return ActionResult.PASS;
        else {
            doRisingUppercut(playerIn.world, playerIn, stack);
            stack.damage(1, playerIn, (entity) -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack itemstack = player.getMainHandStack();
            //Work for rising uppercut
            if (player.isSneaking()) {
                if (!world.isClient()) {
                    doRisingUppercut(world, player, itemstack);
                    itemstack.damage(1, player, (entity) -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
                return TypedActionResult.success(itemstack);
            }
            //Work for rocket punch
            else {
                player.setCurrentHand(hand);
                return TypedActionResult.consume(itemstack);
            }
        } else {
            return TypedActionResult.pass(player.getOffHandStack());
        }

    }

    //Execute Rising Uppercut
    void doRisingUppercut(World world, PlayerEntity player, ItemStack itemStack) {
        //Slightly enlarge player's hitbox
        Box collideBox = SkillData.shouldRisingUppercutAOE(itemStack) ?
                player.getBoundingBox().expand(2, 0, 2) :
                player.getBoundingBox().stretch(RotationUtil.getHorizentalLookVecX(player) * 3, 0, RotationUtil.getHorizentalLookVecZ(player) * 3);


        //Collision Detection
        List<LivingEntity> checks = player.world
                .getNonSpectatingEntities(LivingEntity.class, collideBox);
        checks.remove(player);

        RisingUppercutWatcher watchEntity = new RisingUppercutWatcher(player.world, player.getBlockPos(), player,
                SkillData.getRisingUppercutUpwardTime(itemStack), SkillData.getRisingUppercutFloatingTime(itemStack),
                SkillData.getRisingUppercutSpeedIndex(itemStack), SkillData.getRisingUppercutDamage(itemStack),
                SkillData.shouldIgnoreArmor(itemStack), SkillData.shouldHeal(itemStack), SkillData.shouldBeFireDamage(itemStack));
        if (!checks.isEmpty()) {
            for (LivingEntity livingEntity : checks) {
                watchEntity.watch(livingEntity);
            }
        }
        world.spawnEntity(watchEntity);
        if (SkillData.shouldApplySoftLanding(itemStack)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.SAFE_LANDING, SkillData.getSoftLandingDefaultDuration(itemStack)));
        }
        player.getItemCooldownManager().set(this, SkillData.getRisingUppercutCooldown(itemStack));
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
