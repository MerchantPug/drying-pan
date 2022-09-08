package com.github.merchantpug.dryingpan.mixin.client;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends BipedModel<T> {
    @Shadow @Final public ModelRenderer leftSleeve;

    @Shadow @Final public ModelRenderer rightSleeve;

    public PlayerModelMixin(float dilation) {
        super(dilation);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "TAIL"))
    private void dryingpan$setupDryingPanAnim(T player, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (player instanceof PlayerFryingPanAccess && ((PlayerFryingPanAccess)player).dryingpan$getUsingFryingPan()) {
            if (((PlayerFryingPanAccess)(Object)player).dryingpan$getUsingFryingPanHand() == Hand.MAIN_HAND && player.getMainArm() == HandSide.RIGHT || ((PlayerFryingPanAccess)(Object)player).dryingpan$getUsingFryingPanHand() == Hand.OFF_HAND && player.getMainArm() == HandSide.LEFT) {
                this.rightArm.xRot = (float) (190.0F * Math.PI / 180.0F);
                this.rightArm.yRot = (float) (90.0F * Math.PI / 180.0F);
                this.rightArm.zRot = 0.0F;
                this.rightSleeve.copyFrom(this.rightArm);
            } else if (((PlayerFryingPanAccess)(Object)player).dryingpan$getUsingFryingPanHand() == Hand.OFF_HAND && player.getMainArm() == HandSide.RIGHT || ((PlayerFryingPanAccess)(Object)player).dryingpan$getUsingFryingPanHand() == Hand.MAIN_HAND && player.getMainArm() == HandSide.LEFT) {
                this.leftArm.xRot = (float) -(190.0F * Math.PI / 180.0F);
                this.leftArm.yRot = (float) (90.0F * Math.PI / 180.0F);
                this.leftArm.zRot = 0.0F;
                this.leftSleeve.copyFrom(this.leftArm);
            }
        }
    }
}
