package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.Warhammer;
import com.forestbat.warhammer.items.itemtools.SpaceRings;
import jline.internal.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;

import static java.lang.Math.abs;

public class PipesConnector extends EntityMachineBase {
    public static boolean canBurn;
    public static boolean canWorkBench;
    public static boolean canSpawnStone;
    public static boolean inifinityWater;
    public static PipesConnector start;

    public boolean isConnectedWith(@Nullable TileEntity dest) {
        int xAspect=0, yAspect=0,zAspect=0;
        World world=start.getWorld();
        BlockPos startPos=start.pos;
        BlockPos destPos=dest.getPos();
        boolean neighBoor= destPos==startPos.up()||destPos==startPos.down()||destPos==startPos.west()||
                destPos==startPos.east()||destPos==startPos.north()||destPos==startPos.south();
        Iterator<BlockPos> blockPosIterator=BlockPos.getAllInBox(startPos,destPos).iterator();
        while (blockPosIterator.hasNext()) {
            BlockPos middle = blockPosIterator.next();
            //todo if(dest==null);
            if (neighBoor) return true;
            if (world.getBlockState(middle).getBlock().equals(Warhammer.Blocks.Pipeline) || world.getTileEntity(middle) instanceof PipesConnector) {
                if (world.getBlockState(middle.up()).getBlock().equals(Warhammer.Blocks.Pipeline) || world.getBlockState(middle.down()).getBlock().equals(Warhammer.Blocks.Pipeline))
                    yAspect += 1;
                if (world.getBlockState(middle.west()).getBlock().equals(Warhammer.Blocks.Pipeline) || world.getBlockState(middle.east()).getBlock().equals(Warhammer.Blocks.Pipeline))
                    xAspect += 1;
                if (world.getBlockState(middle.north()).getBlock().equals(Warhammer.Blocks.Pipeline) || world.getBlockState(middle.south()).getBlock().equals(Warhammer.Blocks.Pipeline))
                    zAspect += 1;
                if (world.getTileEntity(middle.up()) instanceof PipesConnector || world.getTileEntity(middle.down()) instanceof PipesConnector)
                    yAspect += 1;
                if (world.getTileEntity(middle.west()) instanceof PipesConnector || world.getTileEntity(middle.east()) instanceof PipesConnector)
                    xAspect += 1;
                if (world.getTileEntity(middle.north()) instanceof PipesConnector || world.getTileEntity(middle.south()) instanceof PipesConnector)
                    zAspect += 1;
            }
        }
        return xAspect>=2*abs(destPos.getX()-startPos.getX())-2 && yAspect>=2*abs(destPos.getY())-startPos.getY()-2 &&
                zAspect>=2*abs(destPos.getZ()-startPos.getZ())-2;
    }
    public void autoSmelt(PipesConnector pipesConnector){
        ItemStack waitBurnStack=pipesConnector.getStackInSlot(0);
        if(canBurn){
            Map smeltList=FurnaceRecipes.instance().getSmeltingList();
            if(smeltList.containsKey(waitBurnStack)){
                pipesConnector.setStackInSlot(ItemStack.EMPTY,0);
                pipesConnector.setStackInSlot(FurnaceRecipes.instance().getSmeltingResult(waitBurnStack),0);
            }
        }
    }
    public void autoSpawnStone(PipesConnector pipesConnector,SpaceRingReader spaceRingReader){
        SpaceRings spaceRings=new SpaceRings();
        if(canSpawnStone&&isConnectedWith(spaceRingReader)){
            spaceRings.input(new ItemStack(Blocks.STONE),1);
        }
    }
    public void autoTransport(EntityPlayer player,TileEntity dest){
        //todo
    }


}
