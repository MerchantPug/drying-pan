package com.github.merchantpug.dryingpan.access;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nullable;

public interface PlayerFryingPanAccess {
    boolean dryingpan$getUsingFryingPan();
    ItemStack dryingpan$getUsingFryingPanStack();
    @Nullable Hand dryingpan$getUsingFryingPanHand();
    void dryingpan$setUsingFryingPan(boolean value, @Nullable ItemStack stack, @Nullable Hand hand);
}
