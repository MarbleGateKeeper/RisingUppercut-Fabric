package love.marblegate.risinguppercut.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ModDamageSource {

    public static DamageSource uppercut(PlayerEntity playerEntity){
        return new RisingUppercutDamageSource(playerEntity);
    }

    public static DamageSource impactWall(PlayerEntity playerEntity){
        return new RocketPunchOnWallDamageSource(playerEntity);
    }

    public static DamageSource punch(PlayerEntity playerEntity){
        return new RocketPunchDamageSource(playerEntity);
    }

    private static class RisingUppercutDamageSource extends EntityDamageSource {

        public RisingUppercutDamageSource(PlayerEntity playerEntity) {
            super("rising_uppercut.rising_uppercut", playerEntity);
        }

        /**
         * Gets the death message that is displayed when the player dies
         */
        public Text getDeathMessage(LivingEntity entityLivingBaseIn) {
            String s = "death.attack." + name;
            return new TranslatableText(s, entityLivingBaseIn.getDisplayName(), source);
        }
    }

    private static class RocketPunchDamageSource extends EntityDamageSource {

        public RocketPunchDamageSource(PlayerEntity playerEntity) {
            super("rising_uppercut.rocket_punch", playerEntity);
        }

        /**
         * Gets the death message that is displayed when the player dies
         */
        public Text getDeathMessage(LivingEntity entityLivingBaseIn) {
            String s = "death.attack." + name;
            return new TranslatableText(s, entityLivingBaseIn.getDisplayName(), source);
        }
    }

    private static class RocketPunchOnWallDamageSource extends EntityDamageSource {

        public RocketPunchOnWallDamageSource(PlayerEntity playerEntity) {
            super("rising_uppercut.rocket_punch_on_wall", playerEntity);
        }

        /**
         * Gets the death message that is displayed when the player dies
         */
        public Text getDeathMessage(LivingEntity entityLivingBaseIn) {
            String s = "death.attack." + name;
            return new TranslatableText(s, entityLivingBaseIn.getDisplayName(), source);
        }
    }


}
