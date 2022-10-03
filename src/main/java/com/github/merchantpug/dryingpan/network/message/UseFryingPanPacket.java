package com.github.merchantpug.dryingpan.network.message;

import com.github.merchantpug.dryingpan.DryingPan;
import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import com.github.merchantpug.dryingpan.network.DryingPanNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class UseFryingPanPacket {
    private int entityId;
    @Nullable private Hand hand;
    private boolean value;

    public UseFryingPanPacket(int entityId, @Nullable Hand hand, boolean value) {
        this.entityId = entityId;
        this.hand = hand;
        this.value = value;
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(hand != null);
        if (hand != null) {
            buf.writeEnum(hand);
        }
        buf.writeBoolean(value);
    }

    public static UseFryingPanPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        @Nullable Hand hand = null;
        if (buf.readBoolean()) {
            hand = buf.readEnum(Hand.class);
        }
        boolean value = buf.readBoolean();
        return new UseFryingPanPacket(entityId, hand, value);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Entity entity = Minecraft.getInstance().level.getEntity(entityId);
                if (!(entity instanceof PlayerEntity)) {
                    DryingPan.LOGGER.warn("Could not find valid player with Drying Pan.");
                    return;
                }
                PlayerEntity player = (PlayerEntity)entity;
                ((PlayerFryingPanAccess)player).dryingpan$setUsingFryingPan(value, hand == null ? null : player.getItemInHand(hand), hand);
            });
        });
        context.get().setPacketHandled(true);
    }
}
