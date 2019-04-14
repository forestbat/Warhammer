package com.forestbat.warhammer.items.itembombs;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Random;

public class OreFinderBomb extends BombBase {
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, EnumHand handIn) {
        bombAction(playerIn,worldIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void bombAction(EntityPlayer player, World world){
        super.bombAction(player,world);
        BlockPos blockPos=shootCalculator(player,world);
        Iterator<BlockPos> blockPosIterator=BlockPos.getAllInBox(blockPos.getX()-24,blockPos.getY()-24,blockPos.getZ()-24,
                    blockPos.getX()+24,blockPos.getY()+24,blockPos.getZ()+24).iterator();
        while(blockPosIterator.hasNext()) {
            IBlockState mineBlockState = world.getBlockState(blockPosIterator.next());
            Block mineBlock = mineBlockState.getBlock();
            //todo Players can add OreDictionary
            if(mineBlock!=Blocks.AIR && mineBlock!=Blocks.STONE && mineBlock!=Blocks.DIRT && mineBlock!=Blocks.COBBLESTONE && mineBlock!=Blocks.GRASS) {
                world.setBlockToAir(blockPosIterator.next());
                Item droppedItem=mineBlock.getItemDropped(mineBlockState,new Random(),3);
                if(droppedItem!=null) {
                    ItemStack droppedItemStack = droppedItem.getDefaultInstance();
                    Block chest = generateChest(world, blockPos);
                    IItemHandler iItemHandler = world.getTileEntity(blockPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    ItemHandlerHelper.insertItemStacked(iItemHandler, droppedItemStack, false);
                }
            }
          }
        }
    public Block generateChest(World world,BlockPos blockPos){
        if(world.setBlockState(blockPos,Blocks.CHEST.getDefaultState()))
        return Blocks.CHEST;
        else return null;
    }
}
