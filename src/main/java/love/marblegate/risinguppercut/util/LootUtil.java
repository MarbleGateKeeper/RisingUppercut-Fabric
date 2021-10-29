package love.marblegate.risinguppercut.util;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

//TODO I believe we need something more reliable and predictable
public class LootUtil {
    public static final LootContextType REQUIRED_TYPE = LootContextType.create().require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.DAMAGE_SOURCE).allow(LootContextParameters.KILLER_ENTITY).allow(LootContextParameters.DIRECT_KILLER_ENTITY)
            .allow(LootContextParameters.LAST_DAMAGE_PLAYER).build();

    public static void dropLoot(LivingEntity livingEntity, DamageSource damageSourceIn, boolean attackedRecently, PlayerEntity player) {
        Identifier identifier = livingEntity.getLootTable();
        LootTable loottable = livingEntity.world.getServer().getLootManager().getTable(identifier);
        LootContext.Builder lootcontext$builder = getLootContextBuilder(livingEntity, attackedRecently, damageSourceIn, player);
        LootContext ctx = lootcontext$builder.build(REQUIRED_TYPE);
        loottable.generateLoot(ctx).forEach(livingEntity::dropStack);
    }

    public static LootContext.Builder getLootContextBuilder(LivingEntity livingEntity, boolean attackedRecently, DamageSource damageSourceIn, PlayerEntity player) {
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) livingEntity.world)).random(livingEntity.getRandom())
                .parameter(LootContextParameters.THIS_ENTITY, livingEntity).parameter(LootContextParameters.ORIGIN, livingEntity.getPos())
                .parameter(LootContextParameters.DAMAGE_SOURCE, damageSourceIn).optionalParameter(LootContextParameters.KILLER_ENTITY, damageSourceIn.getAttacker())
                .optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, damageSourceIn.getSource());
        if (attackedRecently && player != null) {
            lootcontext$builder = lootcontext$builder.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, player).luck(player.getLuck());
        }
        return lootcontext$builder;
    }
}