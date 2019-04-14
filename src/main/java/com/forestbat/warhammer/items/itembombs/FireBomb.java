package com.forestbat.warhammer.items.itembombs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FireBomb extends BombBase{
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, EnumHand handIn) {
        bombAction(playerIn,worldIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    @Override
    public void bombAction(EntityPlayer player, World world) {
        BlockPos blockPos=shootCalculator(player,world);
            world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
    }
    public static class PurpleFire extends Block {
        //public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {

        //}
        public PurpleFire(){
            super(Material.FIRE);
        }
        public void tryCatchPurpleFire(World world, BlockPos pos, EnumFacing facing){
            if(world.getBlockState(pos).getBlock().isFlammable(world,pos,facing)) {
                Timer timer=new Timer(20);
                world.setBlockState(pos, new PurpleFire().getDefaultState());
                if(timer.elapsedTicks>5)
                    world.setBlockToAir(pos);
            }
        }
    }
}
