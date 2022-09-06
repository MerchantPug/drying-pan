package com.github.merchantpug.dryingpan.mixin;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "stopUsingItem", at = @At("HEAD"))
    private void dryingpan$stopUsingFryingPan(CallbackInfo ci) {
        if ((LivingEntity)(Object)this instanceof PlayerEntity) {
            ((PlayerFryingPanAccess)this).dryingpan$setUsingFryingPan(false, null, null);
        }
    }
}
