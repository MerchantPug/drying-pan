package com.github.merchantpug.dryingpan.mixin.client;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.client.particle.RainParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RainParticle.class)
public abstract class RainParticleMixin extends SpriteTexturedParticle {
    protected RainParticleMixin(ClientWorld world, double d, double e, double f) {
        super(world, d, e, f);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;<init>(DDD)V"))
    private void dryingpan$removeWhenHittingDryingPanPlayer(CallbackInfo ci) {
        AxisAlignedBB box = this.getBoundingBox().inflate(0.0F, this.getBoundingBox().getYsize() * 0.15F, 0.0F);
        if (this.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(box.minX, box.max(Direction.Axis.Y), box.minZ, box.maxX, box.max(Direction.Axis.Y), box.maxZ), entity -> ((PlayerFryingPanAccess)entity).dryingpan$getUsingFryingPan()).size() > 0) {
            this.remove();
        }
    }
}
