package com.github.merchantpug.dryingpan.client;

import com.github.merchantpug.dryingpan.DryingPan;
import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import com.github.merchantpug.dryingpan.registry.DryingPanItems;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DryingPanItemModelsProperties {
    public static void register() {
        ItemModelsProperties.register(DryingPanItems.FRYING_PAN.get(), new ResourceLocation("dryingpan", "drying"), (stack, world, entity) -> {
            if (entity instanceof AbstractClientPlayerEntity) {
                return ((PlayerFryingPanAccess)entity).dryingpan$getUsingFryingPanStack() == stack ? 1.0F : 0.0F;
            }
            return 0.0F;
        });
    }
}
