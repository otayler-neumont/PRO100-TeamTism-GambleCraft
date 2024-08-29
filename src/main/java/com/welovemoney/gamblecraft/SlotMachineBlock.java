package com.welovemoney.gamblecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.Block;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SlotMachineBlock extends Block implements EntityBlock {
    boolean isRunning = false;
    public static IntegerProperty TEXTURE = IntegerProperty.create("texture", 1, 125);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;



    public SlotMachineBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.registerDefaultState(this.stateDefinition.any().setValue(TEXTURE, 1));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(TEXTURE);
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
        if(!level.isClientSide && !isRunning) {
            ItemStack heldItem = player.getItemInHand(hand);
            Random random = new Random();
            Result rollResult = null;
            ArrayList<ItemStack> items = null;

            if (!heldItem.isEmpty()) {
                switch (heldItem.getItem().toString()){
                    case("gold_ingot"): {
                        isRunning = true;
                        Pair<Result, String[]> result = SlotSpinLogic.rollOne(new Random());
                        rollResult = result.getLeft();
                        heldItem.shrink(1);
                        items = SlotReward.goldRoleReward(rollResult, player, level, hit, hand, state);
                        level.playSound(null, pos, ModSounds.SLOT_MACHINE_SPIN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        runAnimation(level,pos,state, result.getRight(),items);

                        break;
                    }
                    case("diamond"): {
                        isRunning = true;
                        Pair<Result, String[]> result = SlotSpinLogic.rollOne(new Random());
                        rollResult = result.getLeft();
                        heldItem.shrink(1);
                        items = SlotReward.diamondRoleReward(rollResult, player, level, hit, hand, state);
                        level.playSound(null, pos, ModSounds.SLOT_MACHINE_SPIN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        runAnimation(level,pos,state, result.getRight(),items);

                        break;
                    }
                    case("emerald"): {
                        isRunning = true;
                        Pair<Result, String[]> result = SlotSpinLogic.rollOne(new Random());
                        rollResult = result.getLeft();
                        items = SlotReward.emeraldRoleReward(rollResult);
                        heldItem.shrink(1);
                        assert items != null;
                        level.playSound(null, pos, ModSounds.SLOT_MACHINE_SPIN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        runAnimation(level,pos,state, result.getRight(),items);


                        break;
                    }
                    default:
                        break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    public Dictionary<String,Integer> slotValues = new Hashtable<>();

    private void runAnimation(Level level, BlockPos pos, BlockState state, String[] results, ArrayList<ItemStack> items) {

        slotValues.put("apple", 1);
        slotValues.put("diamond", 2);
        slotValues.put("netherite", 3);
        slotValues.put("netherstar", 4);
        slotValues.put("potato", 5);


        int firstSlot = slotValues.get(results[0]);
        int secondSlot = slotValues.get(results[1]);
        int thirdSlot = slotValues.get(results[2]);
        Random rand = new Random();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int timePassed = 0;
            @Override
            public void run() {
                int newTextureIndex;

                timePassed+= 250;

                if (timePassed >= 6000) { //sets last slot
                    newTextureIndex = (((firstSlot - 1) * 25)+1 ) + ((secondSlot - 1) * 5) + (thirdSlot - 1);
                    System.out.println("its over");
                    timePassed = 0;
                    isRunning = false;
                    dropItem(level, pos, items, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
                    this.cancel();


                } else if (timePassed >= 5000) { //sets middle slot
                    newTextureIndex = (((firstSlot - 1) * 25) + 1) + ((secondSlot - 1) * 5) + rand.nextInt(5);
                    System.out.println("five seconds reached");


                } else if (timePassed >= 4000) {//sets first slot
                    newTextureIndex = (firstSlot - 1) * 25 + 1 + rand.nextInt(25);
                    System.out.println("four seconds reached");


                } else {
                    newTextureIndex = 1+ rand.nextInt(125);
                    System.out.println("Changed");

                }

                String textureName = SlotMachineTextures.getTexture(newTextureIndex);



                BlockState newState = state.setValue(SlotMachineBlock.TEXTURE, newTextureIndex);

                System.out.println(newTextureIndex);
                level.setBlock(pos, newState, 3); // Update the block in the world
                level.sendBlockUpdated(pos, state, newState, 3);

            }
        };
        timer.scheduleAtFixedRate(task, 0, 250);

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
