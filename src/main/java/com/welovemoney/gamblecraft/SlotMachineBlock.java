package com.welovemoney.gamblecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.OutgoingChatMessage;
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
            System.out.println("Block Clicked");

            if (!heldItem.isEmpty()) {
                System.out.println("Item Held: " + heldItem);
                ItemStack carrot = new ItemStack(Items.CARROT);
                switch (heldItem.getItem().toString()){
                    case("gold_ingot") : {
                        System.out.println("Dropping Carrot");
                        dropItem(level,pos,carrot);
                        heldItem.shrink(1);
                        break;
                    }
                    case("diamond") : {
                        System.out.println("Dropping Carrots");
                        dropItem(level,pos,carrot);
                        heldItem.shrink(1);
                        break;
                    }
                    case("emerald") : {
                        System.out.println("Dropping a Carrot");
                        dropItem(level,pos,carrot);
                        heldItem.shrink(1);
                        break;
                    } default: break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void dropItem(Level level, BlockPos pos, ItemStack stack) {
        // Create an ItemEntity and set its position
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }
}
