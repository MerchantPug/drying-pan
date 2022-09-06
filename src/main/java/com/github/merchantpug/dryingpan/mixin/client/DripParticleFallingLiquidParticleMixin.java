package com.github.merchantpug.dryingpan.mixin.client;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/particle/DripParticle$FallingLiquidParticle")
public abstract class DripParticleFallingLiquidParticleMixin extends SpriteTexturedParticle {
    @Shadow @Final protected IParticleData landParticle;

    protected DripParticleFallingLiquidParticleMixin(ClientWorld world, double d, double e, double f) {
        super(world, d, e, f);
    }

    @Inject(method = "postMoveUpdate", at = @At(value = "TAIL"))
    private void dryingpan$removeWhenHittingDryingPanPlayer(CallbackInfo ci) {
        AxisAlignedBB box = this.getBoundingBox().inflate(0.0F, this.getBoundingBox().getYsize() * 0.15F, 0.0F);
        if (this.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(box.minX, box.max(Direction.Axis.Y), box.minZ, box.maxX, box.max(Direction.Axis.Y), box.maxZ), entity -> ((PlayerFryingPanAccess)entity).dryingpan$getUsingFryingPan()).size() > 0) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
        }
    }
}
