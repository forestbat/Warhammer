package com.forestbat.warhammer.items.itembombs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class FishBomb extends BombBase {
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, EnumHand handIn) {
        bombAction(playerIn,worldIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    public void bombAction(EntityPlayer player,World world){
        BlockPos blockPos=shootCalculator(player,world);
        BlockPos[] waterPos={blockPos,blockPos.west(),blockPos.east(),blockPos.north(),blockPos.south(),blockPos.west().north(),blockPos.west().south(),
        blockPos.east().north(),blockPos.east().south()};
        for(BlockPos pos:waterPos)
           if(world.getBlockState(pos).getBlock()== Blocks.WATER)
              player.dropItem(Items.COOKED_FISH,5+new Random().nextInt(16));
        }
    }

