package com.forestbat.warhammer.items.itembombs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class SkyRoarBomb extends BombBase {
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn,EnumHand handIn) {
        bombAction(playerIn,worldIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    @Override
    public void bombAction(EntityPlayer player,World world) {
        BlockPos blockPos=shootCalculator(player,world);
        destroyBlock(world,blockPos,64,true);
    }

    public void destroyBlock(World world,BlockPos pos,int radius,boolean dropBlock){
        Iterator<BlockPos> destroyIterator=getAllinCircle(pos, radius);
        while(destroyIterator.hasNext()) {
            BlockPos blockPos = destroyIterator.next();
            Block block = world.getBlockState(blockPos).getBlock();
            if (dropBlock) {
                block.dropBlockAsItem(world, blockPos, block.getDefaultState(), 1);
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }
    public Iterator<BlockPos> getAllinCircle(BlockPos centralPos,int radius){
        return new Iterator<BlockPos>() {
            int x,y,z;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BlockPos next() {
                BlockPos radiusPos=new BlockPos(x,y,z);
                if((x-centralPos.getX())*(x-centralPos.getX())+
                        (y-centralPos.getY())*(y-centralPos.getY())+
                        (z-centralPos.getZ())*(z-centralPos.getZ())<=radius*radius)
                    return radiusPos;
                else return null;
            }
        };
    }

}
