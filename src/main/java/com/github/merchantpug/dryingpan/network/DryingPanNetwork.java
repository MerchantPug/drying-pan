package com.github.merchantpug.dryingpan.network;

import com.github.merchantpug.dryingpan.network.message.UseFryingPanPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class DryingPanNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("dryingpan", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int i = 0;
        INSTANCE.registerMessage(i++, UseFryingPanPacket.class, UseFryingPanPacket::encode, UseFryingPanPacket::decode, UseFryingPanPacket::handle);
    }
}
