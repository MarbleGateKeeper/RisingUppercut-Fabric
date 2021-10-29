package love.marblegate.risinguppercut.util;

import net.minecraft.entity.LivingEntity;

public class RotationUtil {
    // TODO Need Test
    public static double getHorizentalLookVecX(LivingEntity entity) {
        float yaw = entity.headYaw;
        float f1 = -yaw * ((float) Math.PI / 180F);
        return Math.sin(f1);
    }

    public static double getHorizentalLookVecZ(LivingEntity entity) {
        float yaw = entity.headYaw;
        float f1 = -yaw * ((float) Math.PI / 180F);
        return Math.cos(f1);
    }
}
