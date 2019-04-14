package com.forestbat.warhammer.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCrystals extends Block {
    public BlockCrystals(int meta){
        super(Material.ROCK);
        setHardness(10.0F);
        setResistance(1000F);
        setLightLevel(15F);
    }
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
        return true;
    }
}
