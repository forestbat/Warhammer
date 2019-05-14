package com.forestbat.warhammer.stuff;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.input.ControllerAdapter;

import java.util.Random;

import static com.forestbat.warhammer.configs.WarhammerConfig.*;
import static net.minecraft.init.Items.EMERALD;
import static org.lwjgl.input.Keyboard.KEY_RSHIFT;

public class VanillaStuff {
    /** Why 52? It's AbyssalCraft's Omossol dimension id*/
    @SubscribeEvent
    public void noVoid(TickEvent.PlayerTickEvent event){
        EntityPlayer entityPlayer=event.player;
        if(entityPlayer.getEntityWorld().provider.getDimension()!=52) {
            if (NO_VOID && entityPlayer.posY <= -64) {
                final BlockPos bedLocation = entityPlayer.getBedLocation(entityPlayer.getSpawnDimension());
                entityPlayer.setPositionAndUpdate(bedLocation.getX(),bedLocation.getY() + 1,bedLocation.getZ());
            }
        }
    }
    @SubscribeEvent
    public void yuanMuQiuYu(BlockEvent.HarvestDropsEvent event,Block block){
        if(!GET_FISH_FROM_LOG)
            if(Math.random()<0.01)
            block.quantityDropped(new Random());
    }
    @SubscribeEvent
    public void rewardAchievement(AdvancementEvent advancementEvent){
        if(!advancementEvent.getEntityPlayer().world.isRemote && ADVANCEMENT_REWARD_ITEM!=null) {
            EntityItem entityItem = advancementEvent.getEntityPlayer().
                    dropItem(Item.getByNameOrId(ADVANCEMENT_REWARD_ITEM),ADVANCEMENT_REWARD_AMOUNT);
            if(entityItem!=null)
                entityItem.setNoPickupDelay();
        }
    }
    public ActionResult<ItemStack> onButtonPressed(ControllerAdapter controllerAdapter,EntityPlayer player){
        ItemStack itemStack=player.getHeldItemOffhand();
        return controllerAdapter.isButtonPressed(KEY_LEFT_HAND)&&controllerAdapter.isButtonPressed(KEY_RSHIFT)?
            new ActionResult<>(EnumActionResult.SUCCESS,itemStack):null;
    }
    @SubscribeEvent
    public void gambleStone(PlayerEvent.ItemCraftedEvent event){
        EntityPlayer entityPlayer=event.player;
        if(event.crafting.getItem()==Item.getItemFromBlock(Blocks.STONE_SLAB)&&Math.random()<0.001) {
            EntityItem entityItem = entityPlayer.dropItem(EMERALD, 1);
            if(entityItem!=null)
            entityItem.setNoPickupDelay();
        }
    }
}
