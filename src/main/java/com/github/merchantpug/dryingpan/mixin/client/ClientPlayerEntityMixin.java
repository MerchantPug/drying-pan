package com.github.merchantpug.dryingpan.mixin.client;

import com.github.merchantpug.dryingpan.DryingPan;
import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean dryingpan$dontResetWhenUsingFryingPan(boolean original) {
        return original && !((PlayerFryingPanAccess)(Object)this).dryingpan$getUsingFryingPan();
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;isUsingItem()Z", ordinal = 1))
    private boolean dryingpan$allowSprintingWhenUsingFryingPanOne(boolean original) {
        return original && !((PlayerFryingPanAccess)(Object)this).dryingpan$getUsingFryingPan();
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;isUsingItem()Z", ordinal = 2))
    private boolean dryingpan$allowSprintingWhenUsingFryingPanTwo(boolean original) {
        return original && !((PlayerFryingPanAccess)(Object)this).dryingpan$getUsingFryingPan();
    }
}
