package com.welovemoney.gamblecraft;

import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.internal.objects.annotations.Function;
import org.spongepowered.asm.mixin.Implements;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;
import java.util.Random;


public class SlotMachineBlock extends Block implements EntityBlock{
    public SlotMachineBlock(Properties prop) {
        super(prop);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!level.isClientSide && level instanceof ServerLevel serverLevel) {
            ItemStack heldItem = player.getItemInHand(hand);
            System.out.println("Block Clicked");

            if (!heldItem.isEmpty()) {
                System.out.println("Item Held: " + heldItem);
                switch (heldItem.getItem().toString()){
                    case("gold_ingot") : {
                        System.out.println("Dropping Carrot");
                        ItemStack lootReward = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.BASTION_TREASURE);
                        dropItem(level, pos, lootReward);
                        heldItem.shrink(1);
                        break;
                    }
                    case("diamond") : {
                        System.out.println("Dropping Carrots");
                        ItemStack lootReward = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.END_CITY_TREASURE);
                        dropItem(level, pos, lootReward);
                        heldItem.shrink(1);
                        break;
                    }
                    case("emerald") : {
                        System.out.println("Dropping a Carrot");
                        ItemStack lootReward = grabExistingLoot(player, serverLevel, hit, hand, state, BuiltInLootTables.FISHING_TREASURE);
                        dropItem(level, pos, lootReward);
                        heldItem.shrink(1);
                        break;
                    } default: break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private ItemStack grabExistingLoot(Player player, ServerLevel serverLevel, BlockHitResult hit, InteractionHand hand, BlockState state, ResourceLocation builtinLootTable){
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


    private void dropItem(Level level, BlockPos pos, ItemStack stack) {
        // Create an ItemEntity and set its position
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }
}
