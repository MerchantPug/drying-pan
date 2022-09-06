package com.github.merchantpug.dryingpan.registry;

import com.github.merchantpug.dryingpan.DryingPan;
import com.github.merchantpug.dryingpan.item.FryingPanItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class DryingPanItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, DryingPan.MODID);

    public static final RegistryObject<FryingPanItem> FRYING_PAN = ITEMS.register("frying_pan", () -> new FryingPanItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TOOLS)));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
