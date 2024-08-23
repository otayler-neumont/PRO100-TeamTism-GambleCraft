package com.welovemoney.gamblecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Random;

public class SlotMachineBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SlotMachineBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
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
                    case("gold_ingot"): {
                        rollResult = SlotSpinLogic.rollOne(random);
                        sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        heldItem.shrink(1);
                        items = SlotReward.goldRoleReward(rollResult, player, level, hit, hand, state);
                        dropItem(level, pos, items, state.getValue(BlockStateProperties.FACING));
                        break;
                    }
                    case("diamond"): {
                        /////////////// Change this /////////////////////////////
                        rollResult = SlotSpinLogic.rollOne(random);
                        sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        heldItem.shrink(1);
                        items = SlotReward.diamondRoleReward(rollResult, player, level, hit, hand, state);
                        dropItem(level, pos, items, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
                        break;
                        ////////////////////////////////////////////////////////////
                    }
                    case("emerald"): {
                        rollResult = SlotSpinLogic.rollOne(random);
                        sendMessageToChat((ServerPlayer) player, rollResult.toString());
                        items = SlotReward.emeraldRoleReward(rollResult);
                        heldItem.shrink(1);
                        assert items != null;

                        dropItem(level, pos, items, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void dropItem(Level level, BlockPos pos, ArrayList<ItemStack> items, Direction blockDirection) {
        // Velocity factors based on direction
        double velocity = 0.3;
        double posX = pos.getX() + 0.5 + 0.6 * blockDirection.getStepX();
        double posY = pos.getY() + 0.5;
        double posZ = pos.getZ() + 0.5 + 0.6 * blockDirection.getStepZ();

        // Create an ItemEntity and set its position
        for(ItemStack stack:items) {
            ItemEntity itemEntity = new ItemEntity(level, posX, posY, posZ, stack);
            itemEntity.setDeltaMovement(blockDirection.getStepX() * velocity, 0.1, blockDirection.getStepZ() * velocity);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    public void sendMessageToChat(ServerPlayer player, String message) {
        Component chatMessage = Component.literal(message);
        player.sendSystemMessage(chatMessage);
    }

}
