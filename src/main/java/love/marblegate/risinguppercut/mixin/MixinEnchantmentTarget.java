package love.marblegate.risinguppercut.mixin;

import love.marblegate.risinguppercut.util.ModEnchantmentTarget;
import love.marblegate.risinguppercut.item.Gauntlet;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(EnchantmentTarget.class)
public abstract class MixinEnchantmentTarget {
    private MixinEnchantmentTarget(String name, int ordinal) {

    }

    @Final
    @Mutable
    @Shadow
    private static EnchantmentTarget[] field_9077;

    @Shadow
    public abstract boolean isAcceptableItem(Item item);

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/enchantment/EnchantmentTarget;field_9077:[Lnet/minecraft/enchantment/EnchantmentTarget;",
            shift = At.Shift.AFTER))
    private static void addNewTarget(CallbackInfo ci) {
        List<EnchantmentTarget> variants = new ArrayList<>(Arrays.asList(field_9077));
        EnchantmentTarget last = variants.get(variants.size() - 1);

        // This means our code will still work if other mods or Mojang add more variants!
        // Repeat this section if you need more than one entry. Just remember to have unique ordinals!
        ModEnchantmentTarget.GAUNTLET = (EnchantmentTarget) (Object)new MixinEnchantmentTarget("GAUNTLET", last.ordinal() + 1){
            @Override
            public boolean isAcceptableItem(Item item) {
                return item instanceof Gauntlet;
            }
        };
        variants.add(ModEnchantmentTarget.GAUNTLET);

        field_9077 = variants.toArray(new EnchantmentTarget[0]);
    }
}
