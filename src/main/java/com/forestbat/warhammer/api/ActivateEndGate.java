package com.forestbat.warhammer.api;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** By implementing the interface,you can put other items into endergate */
public interface ActivateEndGate{
    default int setAmount(){
        return 6;
    }
    default boolean waste(){
        return true;
    }
    @SubscribeEvent
    default EnumActionResult onItemUse(PlayerInteractEvent event,EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.getBlockState(pos).equals(Blocks.END_PORTAL_FRAME)) {
            activate(event,player,pos,worldIn,hitX,hitY,hitZ);
            return EnumActionResult.SUCCESS;
        }
        else return EnumActionResult.FAIL;
    }
    @SubscribeEvent
    default boolean activate(PlayerInteractEvent event,EntityPlayer player, BlockPos pos, World world, float hitX, float hitY, float hitZ){
        int amount=0;
        ItemStack itemStack=event.getItemStack();
        Item item=itemStack.getItem();
        if(item instanceof ActivateEndGate) {
            world.setBlockState(pos,world.getBlockState(pos).withProperty(BlockEndPortalFrame.EYE,true));
            if(((ActivateEndGate)item).waste()) {
                itemStack.shrink(1);
                amount++;
                if (amount>=setAmount())
                    world.setBlockState(pos,Blocks.END_PORTAL.getDefaultState());//todo mistake
            }
        }
        return false;
    }
}
