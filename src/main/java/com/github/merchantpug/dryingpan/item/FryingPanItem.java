package com.github.merchantpug.dryingpan.item;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FryingPanItem extends Item {
    public FryingPanItem(Properties properties) {
        super(properties);
    }

    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int i) {
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity)entity;

        player.stopUsingItem();
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        player.startUsingItem(hand);
        ((PlayerFryingPanAccess)player).dryingpan$setUsingFryingPan(true, itemStack, hand);
        return ActionResult.consume(itemStack);
    }

    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }
}
