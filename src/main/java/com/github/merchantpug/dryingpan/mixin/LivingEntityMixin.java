package com.github.merchantpug.dryingpan.mixin;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import com.github.merchantpug.dryingpan.network.DryingPanNetwork;
import com.github.merchantpug.dryingpan.network.message.UseFryingPanPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "stopUsingItem", at = @At("HEAD"))
    private void dryingpan$stopUsingFryingPan(CallbackInfo ci) {
        if ((LivingEntity)(Object)this instanceof PlayerEntity) {
            ((PlayerFryingPanAccess)this).dryingpan$setUsingFryingPan(false, null, null);
            if (!this.level.isClientSide) {
                DryingPanNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),  new UseFryingPanPacket(this.getId(),null, false));
            }
        }
    }
}
