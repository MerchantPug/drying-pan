package com.github.merchantpug.dryingpan;

import com.github.merchantpug.dryingpan.registry.DryingPanItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DryingPan.MODID)
public class DryingPan
{
    public static final String MODID = "dryingpan";
    public static final Logger LOGGER = LogManager.getLogger();

    public DryingPan() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> {
        });

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DryingPanItems.init();
    }
}
