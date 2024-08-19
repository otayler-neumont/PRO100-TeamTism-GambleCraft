package com.welovemoney.gamblecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExistingLootPool {

    static ItemStack grabExistingLoot(Player player, ServerLevel serverLevel, BlockHitResult hit, InteractionHand hand, BlockState state, ResourceLocation builtinLootTable){
        LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(builtinLootTable); //Ex loot table: BuiltInLootTables.ANCIENT_CITY

        // Build the LootParams
        LootParams.Builder paramsBuilder = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, hit.getLocation()) // The position where the interaction happened
                .withParameter(LootContextParams.TOOL, player.getItemInHand(hand)) // The item in the player's hand
                .withParameter(LootContextParams.BLOCK_STATE, state) // The blocks state
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player); // The player interacting with the block
        LootParams lootParams = paramsBuilder.create(LootContextParamSets.BLOCK);

        // Generate the loot
        List<ItemStack> loot = lootTable.getRandomItems(lootParams);

        Random random = new Random();
        return loot.get(random.nextInt(loot.size()));
    }

    static ItemStack getEndCityLoot(Result slotReturn, Player player, ServerLevel serverLevel, BlockHitResult hit, InteractionHand hand, BlockState state){
        boolean correctItem = false;
        ArrayList<Item> itemWhitelist = new ArrayList<Item>();

        switch (slotReturn){
            case DOUBLE_APPLE:{
                //Add Items to the whitelist
                itemWhitelist.add(Items.IRON_HORSE_ARMOR);
                itemWhitelist.add(Items.GOLDEN_HORSE_ARMOR);
                itemWhitelist.add(Items.DIAMOND_HORSE_ARMOR);
                itemWhitelist.add(Items.BEETROOT_SEEDS);
                itemWhitelist.add(Items.SADDLE);


                while (!correctItem) {
                    //Grab from the End City loot table
                    ItemStack desiredLoot = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.END_CITY_TREASURE);

                    //Ensure the item is in the whitelist
                    if (itemWhitelist.contains(desiredLoot.getItem())) {
                        System.out.println("Item in whitelist");
                        return desiredLoot;
                    }
                    System.out.println("Invalid Item, Rerolling...");
                }
                itemWhitelist.clear();
            }
            case TRIPLE_APPLE:{
                //Add items to whitelist
                itemWhitelist.add(Items.IRON_PICKAXE);
                itemWhitelist.add(Items.IRON_SHOVEL);
                itemWhitelist.add(Items.IRON_SWORD);
                itemWhitelist.add(Items.IRON_INGOT);
                itemWhitelist.add(Items.GOLD_INGOT);

                while (!correctItem) {
                    //Grab from the End City loot table
                    ItemStack desiredLoot = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.END_CITY_TREASURE);

                    //Ensure the item is in the whitelist
                    if (itemWhitelist.contains(desiredLoot.getItem())) {
                        if (desiredLoot.is(Items.IRON_INGOT) || desiredLoot.is(Items.GOLD_INGOT)) {
                            System.out.println("Item in whitelist");
                            return desiredLoot;
                        } else if (desiredLoot.isEnchanted()) {
                            System.out.println("Item in whitelist and enchanted");
                            return desiredLoot;
                        }
                    }
                    System.out.println("Invalid Item, Rerolling...");
                }
                itemWhitelist.clear();
            }
            case DOUBLE_DIAMOND:{
                //Add items to whitelist
                itemWhitelist.add(Items.DIAMOND_PICKAXE);
                itemWhitelist.add(Items.DIAMOND_SHOVEL);
                itemWhitelist.add(Items.DIAMOND_SWORD);
                itemWhitelist.add(Items.IRON_HELMET);
                itemWhitelist.add(Items.IRON_CHESTPLATE);
                itemWhitelist.add(Items.IRON_LEGGINGS);
                itemWhitelist.add(Items.IRON_BOOTS);

                while (!correctItem) {
                    //Grab Item from End City loot table
                    ItemStack desiredLoot = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.END_CITY_TREASURE);

                    //Ensure the item is in the whitelist
                    if (itemWhitelist.contains(desiredLoot.getItem()) && desiredLoot.isEnchanted()) {
                        System.out.println("Item in whitelist and enchanted");
                        return desiredLoot;
                    }
                    System.out.println("Invalid Item, Rerolling...");
                }
                itemWhitelist.clear();
            }
            case TRIPLE_DIAMOND:{
                //Add items to whitelist
                itemWhitelist.add(Items.DIAMOND_HELMET);
                itemWhitelist.add(Items.DIAMOND_CHESTPLATE);
                itemWhitelist.add(Items.DIAMOND_LEGGINGS);
                itemWhitelist.add(Items.DIAMOND_BOOTS);
                itemWhitelist.add(Items.EMERALD);
                itemWhitelist.add(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);

                while (!correctItem) {
                    //Grab Item from End City Loot table
                    ItemStack desiredLoot = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.END_CITY_TREASURE);

                    //Ensure the item is in the whitelist
                    if (itemWhitelist.contains(desiredLoot.getItem())) {
                        if (desiredLoot.is(Items.EMERALD) || desiredLoot.is(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)) {
                            System.out.println("Item in whitelist");
                            return desiredLoot;
                        } else if (desiredLoot.isEnchanted()) {
                            System.out.println("Item in whitelist and enchanted");
                            return desiredLoot;
                        }
                    }
                    System.out.println("Invalid Item, Rerolling...");
                }
                itemWhitelist.clear();
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + slotReturn);
        }
        return null;
    }
}
