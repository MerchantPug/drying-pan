package com.github.merchantpug.dryingpan.client;

import com.github.merchantpug.dryingpan.DryingPan;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DryingPan.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DryingPanClient {

    @SubscribeEvent
    public static void renderPanPostEventListener(FMLClientSetupEvent event) {
        event.enqueueWork(DryingPanItemModelsProperties::register);
    }
}
