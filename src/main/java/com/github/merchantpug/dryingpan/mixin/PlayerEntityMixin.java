package com.github.merchantpug.dryingpan.mixin;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerFryingPanAccess {
    @Unique private boolean dryingpan$usingFryingPan;
    @Nullable @Unique private ItemStack dryingpan$fryingPanStack;
    @Nullable @Unique private Hand dryingpan$fryingPanHand;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean dryingpan$getUsingFryingPan() {
        return dryingpan$usingFryingPan;
    }

    @Nullable
    @Override
    public ItemStack dryingpan$getUsingFryingPanStack() {
        return dryingpan$fryingPanStack;
    }

    @Nullable
    @Override
    public Hand dryingpan$getUsingFryingPanHand() {
        return dryingpan$fryingPanHand;
    }

    @Override
    public void dryingpan$setUsingFryingPan(boolean value, @Nullable ItemStack stack, @Nullable Hand hand) {
        dryingpan$usingFryingPan = value;
        dryingpan$fryingPanStack = stack;
        dryingpan$fryingPanHand = hand;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void dryingpan$tickRainSplashes(CallbackInfo ci) {
        if (this.level.isRainingAt(this.blockPosition()) && this.dryingpan$usingFryingPan && !this.level.isClientSide && this.tickCount % 2 == 0) {
            AxisAlignedBB box = this.getBoundingBox().inflate(-(this.getBoundingBox().getXsize() * 0.2F), this.getBoundingBox().getYsize() * 0.15F, -(this.getBoundingBox().getZsize() * 0.2F));
            ((ServerWorld)this.level).sendParticles(ParticleTypes.RAIN, (this.getRandom().nextFloat() * (box.maxX - box.minX) + box.minX), box.max(Direction.Axis.Y), (this.getRandom().nextFloat() * (box.maxZ - box.minZ) + box.minZ), 1, 0.0D, 0.0D, 0.0D, 1.0D);
        }
        if (this.dryingpan$usingFryingPan) {
            List<FallingBlockEntity> fallingBlockEntityList = this.level.getEntities(EntityType.FALLING_BLOCK, this.getBoundingBox().inflate(0.0F, this.getBoundingBox().getYsize() * 0.15F, 0.0F), p -> true);
            fallingBlockEntityList.forEach(fallingBlockEntity -> {
                if (fallingBlockEntity.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    fallingBlockEntity.spawnAtLocation(fallingBlockEntity.getBlockState().getBlock());
                }
                fallingBlockEntity.remove();
            });
        }
    }
}
