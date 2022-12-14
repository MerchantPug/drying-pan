package com.github.merchantpug.dryingpan.item;

import com.github.merchantpug.dryingpan.access.PlayerFryingPanAccess;
import com.github.merchantpug.dryingpan.network.DryingPanNetwork;
import com.github.merchantpug.dryingpan.network.message.UseFryingPanPacket;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Set;
import java.util.UUID;

public class FryingPanItem extends Item {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public FryingPanItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 3.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3.1F, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(UUID.fromString("f3b0913a-a4c1-4f43-8edc-5deff015cbaa"), "Weapon modifier", 2.0F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
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
        if (!player.level.isClientSide) {
            DryingPanNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),  new UseFryingPanPacket(player.getId(), hand, true));
        }
        return ActionResult.consume(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(2, entity, (entity1) -> {
                entity1.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        Set<Enchantment> enchantments = ImmutableSet.of(Enchantments.SHARPNESS, Enchantments.MOB_LOOTING, Enchantments.FIRE_ASPECT, Enchantments.KNOCKBACK, Enchantments.UNBREAKING, Enchantments.MENDING, Enchantments.VANISHING_CURSE);
        return enchantments.containsAll(EnchantmentHelper.getEnchantments(book).keySet());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        Set<Enchantment> enchantments = ImmutableSet.of(Enchantments.SHARPNESS, Enchantments.MOB_LOOTING, Enchantments.FIRE_ASPECT, Enchantments.KNOCKBACK, Enchantments.UNBREAKING, Enchantments.MENDING, Enchantments.VANISHING_CURSE);
        return enchantments.contains(enchantment);
    }

    @Override
    public int getEnchantmentValue() {
        return 14;
    }
}
