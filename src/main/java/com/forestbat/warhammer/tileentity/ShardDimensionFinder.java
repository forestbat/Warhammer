package com.forestbat.warhammer.tileentity;

import net.minecraft.util.ITickable;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;

import java.util.Random;

import static com.forestbat.warhammer.configs.WarhammerConfig.*;

public class ShardDimensionFinder extends EntityMachineBase implements ITickable {
    private String dimensionid;

    public boolean generateNewWorld() {
        int generateWorldAmount=0;
        if (getEnergyStored() > 1e9) {
            DimensionManager.registerDimension(dimensionid.hashCode(), DimensionType.OVERWORLD);
            generateWorldAmount+=1;
            markDirty();
        }
        return !(generateWorldAmount>DIMENSION_AMOUNT_CAN_FOUND);
    }

    public void setDimensionInfo(Random rand, World world) {
        if (world.provider.getDimension() == dimensionid.hashCode()) {
            world.getWorldInfo().setBorderSize(rand.nextInt(DIMENSION_BORDER_LENGTH) + 16);
            switch (rand.nextInt(10)) {
                case 3:world.getWorldInfo().setDifficulty(EnumDifficulty.NORMAL);
                case 2:world.getWorldInfo().setDifficulty(EnumDifficulty.EASY);
                case 1:world.getWorldInfo().setDifficulty(EnumDifficulty.PEACEFUL);
                if(rand.nextInt()>3)
                    world.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
            }
            if(Math.random()<0.4)
            world.getWorldInfo().setRaining(true);
            if(Math.random()<0.12)
                world.getWorldInfo().setRaining(true);
            world.setSeaLevel((int)(128*Math.random()));
            world.setSpawnPoint(pos);
            world.setTotalWorldTime((long)(1e5*Math.random()));
            switch (rand.nextInt(5)) {
                case 1:world.getWorldInfo().setTerrainType(WorldType.AMPLIFIED);
                case 2:world.getWorldInfo().setTerrainType(WorldType.DEFAULT);
                case 3:world.getWorldInfo().setTerrainType(WorldType.LARGE_BIOMES);
                case 4:world.getWorldInfo().setTerrainType(WorldType.FLAT);
            }
        }
    }

    public void update() {
        if (world.getWorldTime() % DIMENSION_DISCOVER_TICKS == 0)
            generateNewWorld();
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;
        int energyExtracted = 100000000;
        int energy = getEnergyStored();
        if (!simulate && generateNewWorld())
            energy -= energyExtracted;
        return energy;
    }
}
