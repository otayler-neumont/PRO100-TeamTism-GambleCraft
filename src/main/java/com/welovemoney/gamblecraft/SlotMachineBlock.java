package com.welovemoney.gamblecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.Block;

import java.awt.*;
import java.util.ArrayList;
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
        if(!level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            Random random = new Random();
            Result rollResult = null;
            ArrayList<ItemStack> items = null;


            if (!heldItem.isEmpty()) {

                switch (heldItem.getItem().toString()){
                    case("gold_ingot") : {
                        rollResult = SlotSpinLogic.rollOne(random);
                        sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        items = SlotReward.goldRoleReward(rollResult, player, level, hit, hand, state);
                        dropItem(level, pos, items);
                        heldItem.shrink(1);
                        break;
                    }
                    case("diamond") : {
                    /////////////// Change this /////////////////////////////
                        rollResult = SlotSpinLogic.rollOne(random);
                       // sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        items = SlotReward.diamondRoleReward(Result.TRIPLE_DIAMOND, player, level, hit, hand, state);
                        dropItem(level, pos, items);
                        heldItem.shrink(1);
                        break;
                        ////////////////////////////////////////////////////////////
                    }
                    case("emerald") : {
                        rollResult = SlotSpinLogic.rollOne(random);
                        sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        items = SlotReward.emeraldRoleReward(rollResult);
                        assert items != null;
                        dropItem(level,pos,items);
                        heldItem.shrink(1);
                        break;
                    } default: break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void dropItem(Level level, BlockPos pos, ArrayList<ItemStack> items) {
        // Create an ItemEntity and set its position
        for(ItemStack stack:items) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }
    private void dropItem(Level level, BlockPos pos, ArrayList<ItemStack> items, Direction direction) {
        // Velocity factors based on direction
        double velocityX = 0.0;
        double velocityY = 0.2; // Add a bit of upward motion
        double velocityZ = 0.0;

        // Adjust velocity based on the direction
        switch (direction) {
            case NORTH:
                velocityZ = -0.2;
                break;
            case SOUTH:
                velocityZ = 0.2;
                break;
            case WEST:
                velocityX = -0.2;
                break;
            case EAST:
                velocityX = 0.2;
                break;
            case UP:
                velocityY = 0.4;
                break;
            case DOWN:
                velocityY = -0.4;
                break;
            default:
                break;
        }

        // Create an ItemEntity and set its position
        for(ItemStack stack:items) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setDeltaMovement(velocityX, velocityY, velocityZ);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    private void dropItem(Level level, BlockPos pos, ItemStack item) {
        // Create an ItemEntity and set its position

        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, item);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);

    }

    public void sendMessageToChat(ServerPlayer player, String message) {
        Component chatMessage = Component.literal(message);
        player.sendSystemMessage(chatMessage);
    }

}
