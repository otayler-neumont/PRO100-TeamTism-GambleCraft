package com.welovemoney.gamblecraft;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.Random;

public class SlotReward {

    //region **Reward decision logic**
    public static ArrayList<ItemStack> emeraldRoleReward(Result result){
        ArrayList<ItemStack> returnItems = new ArrayList<>();

        switch (result) {
            case TRIPLE_STAR:
                System.out.println("You got a Triple Star!");


                //Returns 4 books of Mending and 4 books of Protection V
                for(int i = 0; i < 4; i++) {
                    returnItems.add(createEnchantedBook(Enchantments.MENDING,1));
                    returnItems.add(createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION,5));
                }
                return returnItems;

            case DOUBLE_STAR:
                System.out.println("You got a Double Star!");

                //Returns 1 book of Mending and 1 book of Protection V
                returnItems.add(createEnchantedBook(Enchantments.MENDING,1));
                returnItems.add(createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION,5));
                return returnItems;

            case TRIPLE_POTATO:
                System.out.println("You got a Triple Potato!");
                returnItems.add(new ItemStack(Items.EMERALD,3));
                return returnItems;

            case DOUBLE_POTATO:
                System.out.println("You got a Double Potato!");
                returnItems.add(new ItemStack(Items.EMERALD));
                return returnItems;

            case TRIPLE_APPLE:
                System.out.println("You got a Triple Apple!");
                //returns random music disc
                returnItems.add(randomFromArray(discArray()));
                return returnItems;

            case DOUBLE_APPLE:
                System.out.println("You got a Double Apple!");
                //returns random mob head
                returnItems.add(randomFromArray(headArray()));
                return returnItems;

            case TRIPLE_DIAMOND:
                System.out.println("You got a Triple Diamond!");
                for(int i = 0; i < 8; i++){
                    returnItems.add(randomFromArray(enchantedBookArray()));
                }
                return returnItems;

            case DOUBLE_DIAMOND:
                System.out.println("You got a Double Diamond!");
                for(int i = 0; i < 4; i++){
                    returnItems.add(randomFromArray(enchantedBookArray()));
                }
                return returnItems;

            case TRIPLE_WILD:
                System.out.println("You got a Triple Wild!");
                returnItems.add(new ItemStack(Items.EMERALD,64));
                return returnItems;


            case NONE:
                System.out.println("No result.");
                break;

            default:
                System.out.println("Unknown result.");
                break;
        }
        return null;
    }

    public static ArrayList<ItemStack> diamondRoleReward(Result result, Player player, Level level, BlockHitResult hit, InteractionHand hand, BlockState state){
        ArrayList<ItemStack> returnItems = new ArrayList<>();

        switch (result) {
            case TRIPLE_STAR:
                System.out.println("You got a Triple Star!");
                ItemStack enchantedElytra = new ItemStack(Items.ELYTRA);
                enchantedElytra.enchant(Enchantments.MENDING, 1);
                enchantedElytra.enchant(Enchantments.UNBREAKING, 3);
                returnItems.add(enchantedElytra);
                return returnItems;



            case DOUBLE_STAR:
                System.out.println("You got a Double Star!");

                returnItems.add(new ItemStack(Items.ELYTRA));
                return returnItems;

            case TRIPLE_POTATO:
                System.out.println("You got a Triple Potato!");
                returnItems.add(new ItemStack(Items.DIAMOND,3));
                return returnItems;

            case DOUBLE_POTATO:
                System.out.println("You got a Double Potato!");
                returnItems.add(new ItemStack(Items.DIAMOND));
                return returnItems;

            case TRIPLE_APPLE:
                System.out.println("You got a Triple Apple!");
                returnItems.add(ExistingLootPool.getEndCityLoot(Result.TRIPLE_APPLE,player,(ServerLevel) level,hit,hand,state));
                return returnItems;

            case DOUBLE_APPLE:
                System.out.println("You got a Double Apple!");
                returnItems.add(ExistingLootPool.getEndCityLoot(Result.DOUBLE_APPLE,player,(ServerLevel) level,hit,hand,state));
                return returnItems;

            case TRIPLE_DIAMOND:
                System.out.println("You got a Triple Diamond!");
                returnItems.add(ExistingLootPool.getEndCityLoot(Result.TRIPLE_DIAMOND,player,(ServerLevel) level,hit,hand,state));
                return returnItems;

            case DOUBLE_DIAMOND:
                System.out.println("You got a Double Diamond!");
                returnItems.add(ExistingLootPool.getEndCityLoot(Result.DOUBLE_DIAMOND,player,(ServerLevel) level,hit,hand,state));
                return returnItems;

            case TRIPLE_WILD:
                System.out.println("You got a Triple Wild!");

                returnItems.add(new ItemStack(Items.DIAMOND,64));
                return returnItems;

            case NONE:
                System.out.println("No result.");
                break;

            default:
                System.out.println("Unknown result.");
                break;
        }
        return null;
    }

    public static ArrayList<ItemStack> goldRoleReward(Result result, Player player, Level level, BlockHitResult hit, InteractionHand hand, BlockState state){
        ArrayList<ItemStack> returnItems = new ArrayList<>();

        switch (result) {
            case TRIPLE_STAR:
                System.out.println("You got a Triple Star!");
                for(int i = 0; i < 8; i++) {
                    returnItems.add(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));

                }
                return returnItems;

            case DOUBLE_STAR:
                System.out.println("You got a Double Star!");
                for(int i = 0; i < 2; i++) {
                    returnItems.add(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));

                }
                return returnItems;

            case TRIPLE_POTATO:
                System.out.println("You got a Triple Potato!");
                returnItems.add(new ItemStack(Items.DIAMOND,3));
                return returnItems;

            case DOUBLE_POTATO:
                System.out.println("You got a Double Potato!");
                returnItems.add(new ItemStack(Items.DIAMOND));
                return returnItems;

            case TRIPLE_APPLE:
                System.out.println("You got a Triple Apple!");
                returnItems.add(ExistingLootPool.grabExistingLoot(player, (ServerLevel) level, hit, hand, state, BuiltInLootTables.BASTION_BRIDGE));
                return returnItems;

            case DOUBLE_APPLE:
                System.out.println("You got a Double Apple!");
                returnItems.add(ExistingLootPool.grabExistingLoot(player, (ServerLevel) level, hit, hand, state, BuiltInLootTables.BASTION_HOGLIN_STABLE));
                return returnItems;

            case TRIPLE_DIAMOND:
                System.out.println("You got a Triple Diamond!");
                returnItems.add(ExistingLootPool.grabExistingLoot(player, (ServerLevel) level, hit, hand, state, BuiltInLootTables.BASTION_TREASURE));
                return returnItems;

            case DOUBLE_DIAMOND:
                System.out.println("You got a Double Diamond!");
                returnItems.add(ExistingLootPool.grabExistingLoot(player, (ServerLevel) level, hit, hand, state, BuiltInLootTables.BASTION_OTHER));
                return returnItems;


            case TRIPLE_WILD:
                System.out.println("You got a Triple Wild!");

                returnItems.add(new ItemStack(Items.DIAMOND,64));
                return returnItems;

            case NONE:
                System.out.println("No result.");
                break;

            default:
                System.out.println("Unknown result.");
                break;
        }
        return null;
    }
    //endregion


    //region **Enchanted book creation**
    private static ItemStack createEnchantedBook(Enchantment enchantment, int level) {
        // Create an ItemStack of an enchanted book
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);

        // Add an enchantment to the book
        enchantedBook.enchant(enchantment, level);

        return enchantedBook;
    }

    //endregion

    //region **randomFromArray**
    private static ItemStack randomFromArray(Item[] array){
        Random random = new Random();
        return new ItemStack(array[random.nextInt(array.length)]);

    }
    private static ItemStack randomFromArray(ItemStack[] array){
        Random random = new Random();
        return array[random.nextInt(array.length)];

    }
    //endregion

    //region **Item Arrays**

    //ALL ARRAYS CREATED VIA CHAT GPT
    private static Item[] discArray(){

        return new Item[]{
                Items.MUSIC_DISC_13,
                Items.MUSIC_DISC_CAT,
                Items.MUSIC_DISC_BLOCKS,
                Items.MUSIC_DISC_CHIRP,
                Items.MUSIC_DISC_FAR,
                Items.MUSIC_DISC_MALL,
                Items.MUSIC_DISC_MELLOHI,
                Items.MUSIC_DISC_STAL,
                Items.MUSIC_DISC_STRAD,
                Items.MUSIC_DISC_WARD,
                Items.MUSIC_DISC_11,
                Items.MUSIC_DISC_WAIT,
                Items.MUSIC_DISC_PIGSTEP,
                Items.MUSIC_DISC_OTHERSIDE,
                Items.MUSIC_DISC_5,
                Items.MUSIC_DISC_RELIC
        };


    }


    private static Item[] headArray(){

        return new Item[] {
                Items.CREEPER_HEAD,
                Items.ZOMBIE_HEAD,
                Items.SKELETON_SKULL,
                Items.WITHER_SKELETON_SKULL,
                Items.DRAGON_HEAD,
                Items.PLAYER_HEAD,
                Items.PIGLIN_HEAD
        };

    }

    private static ItemStack[] enchantedBookArray(){
        return new ItemStack[]{
                createEnchantedBook(Enchantments.AQUA_AFFINITY, 1),

                createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS, 1),
                createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS, 2),
                createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS, 3),
                createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS, 4),
                createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS, 5),

                createEnchantedBook(Enchantments.BINDING_CURSE, 1),

                createEnchantedBook(Enchantments.CHANNELING, 1),

                createEnchantedBook(Enchantments.DEPTH_STRIDER, 1),
                createEnchantedBook(Enchantments.DEPTH_STRIDER, 2),
                createEnchantedBook(Enchantments.DEPTH_STRIDER, 3),

                createEnchantedBook(Enchantments.BLOCK_EFFICIENCY, 1),
                createEnchantedBook(Enchantments.BLOCK_EFFICIENCY, 2),
                createEnchantedBook(Enchantments.BLOCK_EFFICIENCY, 3),
                createEnchantedBook(Enchantments.BLOCK_EFFICIENCY, 4),
                createEnchantedBook(Enchantments.BLOCK_EFFICIENCY, 5),

                createEnchantedBook(Enchantments.FALL_PROTECTION, 1),
                createEnchantedBook(Enchantments.FALL_PROTECTION, 2),
                createEnchantedBook(Enchantments.FALL_PROTECTION, 3),
                createEnchantedBook(Enchantments.FALL_PROTECTION, 4),

                createEnchantedBook(Enchantments.FIRE_ASPECT, 1),
                createEnchantedBook(Enchantments.FIRE_ASPECT, 2),

                createEnchantedBook(Enchantments.FLAMING_ARROWS, 1),

                createEnchantedBook(Enchantments.BLOCK_FORTUNE, 1),
                createEnchantedBook(Enchantments.BLOCK_FORTUNE, 2),
                createEnchantedBook(Enchantments.BLOCK_FORTUNE, 3),

                createEnchantedBook(Enchantments.FROST_WALKER, 1),
                createEnchantedBook(Enchantments.FROST_WALKER, 2),

                createEnchantedBook(Enchantments.IMPALING, 1),
                createEnchantedBook(Enchantments.IMPALING, 2),
                createEnchantedBook(Enchantments.IMPALING, 3),
                createEnchantedBook(Enchantments.IMPALING, 4),
                createEnchantedBook(Enchantments.IMPALING, 5),

                createEnchantedBook(Enchantments.INFINITY_ARROWS, 1),

                createEnchantedBook(Enchantments.KNOCKBACK, 1),
                createEnchantedBook(Enchantments.KNOCKBACK, 2),

                createEnchantedBook(Enchantments.MOB_LOOTING, 1),
                createEnchantedBook(Enchantments.MOB_LOOTING, 2),
                createEnchantedBook(Enchantments.MOB_LOOTING, 3),

                createEnchantedBook(Enchantments.FISHING_LUCK, 1),
                createEnchantedBook(Enchantments.FISHING_LUCK, 2),
                createEnchantedBook(Enchantments.FISHING_LUCK, 3),

                createEnchantedBook(Enchantments.FISHING_SPEED, 1),
                createEnchantedBook(Enchantments.FISHING_SPEED, 2),
                createEnchantedBook(Enchantments.FISHING_SPEED, 3),

                createEnchantedBook(Enchantments.MULTISHOT, 1),

                createEnchantedBook(Enchantments.PIERCING, 1),
                createEnchantedBook(Enchantments.PIERCING, 2),
                createEnchantedBook(Enchantments.PIERCING, 3),
                createEnchantedBook(Enchantments.PIERCING, 4),

                createEnchantedBook(Enchantments.POWER_ARROWS, 1),
                createEnchantedBook(Enchantments.POWER_ARROWS, 2),
                createEnchantedBook(Enchantments.POWER_ARROWS, 3),
                createEnchantedBook(Enchantments.POWER_ARROWS, 4),
                createEnchantedBook(Enchantments.POWER_ARROWS, 5),

                createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION, 1),
                createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION, 2),
                createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION, 3),
                createEnchantedBook(Enchantments.ALL_DAMAGE_PROTECTION, 4),

                createEnchantedBook(Enchantments.PUNCH_ARROWS, 1),
                createEnchantedBook(Enchantments.PUNCH_ARROWS, 2),

                createEnchantedBook(Enchantments.QUICK_CHARGE, 1),
                createEnchantedBook(Enchantments.QUICK_CHARGE, 2),
                createEnchantedBook(Enchantments.QUICK_CHARGE, 3),

                createEnchantedBook(Enchantments.RESPIRATION, 1),
                createEnchantedBook(Enchantments.RESPIRATION, 2),
                createEnchantedBook(Enchantments.RESPIRATION, 3),

                createEnchantedBook(Enchantments.RIPTIDE, 1),
                createEnchantedBook(Enchantments.RIPTIDE, 2),
                createEnchantedBook(Enchantments.RIPTIDE, 3),

                createEnchantedBook(Enchantments.SHARPNESS, 1),
                createEnchantedBook(Enchantments.SHARPNESS, 2),
                createEnchantedBook(Enchantments.SHARPNESS, 3),
                createEnchantedBook(Enchantments.SHARPNESS, 4),
                createEnchantedBook(Enchantments.SHARPNESS, 5),

                createEnchantedBook(Enchantments.SILK_TOUCH, 1),

                createEnchantedBook(Enchantments.SMITE, 1),
                createEnchantedBook(Enchantments.SMITE, 2),
                createEnchantedBook(Enchantments.SMITE, 3),
                createEnchantedBook(Enchantments.SMITE, 4),
                createEnchantedBook(Enchantments.SMITE, 5),

                createEnchantedBook(Enchantments.SOUL_SPEED, 1),
                createEnchantedBook(Enchantments.SOUL_SPEED, 2),
                createEnchantedBook(Enchantments.SOUL_SPEED, 3),

                createEnchantedBook(Enchantments.SWEEPING_EDGE, 1),
                createEnchantedBook(Enchantments.SWEEPING_EDGE, 2),
                createEnchantedBook(Enchantments.SWEEPING_EDGE, 3),

                createEnchantedBook(Enchantments.SWIFT_SNEAK, 1),
                createEnchantedBook(Enchantments.SWIFT_SNEAK, 2),
                createEnchantedBook(Enchantments.SWIFT_SNEAK, 3),

                createEnchantedBook(Enchantments.THORNS, 1),
                createEnchantedBook(Enchantments.THORNS, 2),
                createEnchantedBook(Enchantments.THORNS, 3),

                createEnchantedBook(Enchantments.UNBREAKING, 1),
                createEnchantedBook(Enchantments.UNBREAKING, 2),
                createEnchantedBook(Enchantments.UNBREAKING, 3),

                createEnchantedBook(Enchantments.VANISHING_CURSE, 1)
        };
    }

    //endregion**

}
