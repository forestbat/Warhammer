package com.forestbat.warhammer.items.itemtools;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class ToolsBase extends Item implements IEnergyStorage {
    public String ENERGY="Energy";
    private static int energy,capacity,maxReceive,maxExtract;
    public ToolsBase(){
         setMaxStackSize(1);
    }

    public int receiveEnergy(ItemStack buffer, int maxReceive, boolean simulate){
        NBTTagCompound nbtTagCompound=new NBTTagCompound();
        if(!buffer.hasTagCompound())
            buffer.setTagCompound(nbtTagCompound);
        nbtTagCompound.setInteger(ENERGY,getEnergyStored());
        return receiveEnergy(maxReceive,simulate);
    }
    public int extractEnergy(ItemStack buffer,int maxExtract,boolean simulate){
        NBTTagCompound nbtTagCompound=new NBTTagCompound();
        if(!buffer.hasTagCompound())
            buffer.setTagCompound(nbtTagCompound);
        nbtTagCompound.setInteger(ENERGY,getEnergyStored());
        return extractEnergy(maxExtract,simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        if (!canReceive())
            return 0;
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        if (!canExtract())
            return 0;
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }
    @Override
    public int getEnergyStored()
    {
        return energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return capacity;
    }

    @Override
    public boolean canExtract()
    {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive()
    {
        return this.maxReceive > 0;
    }

    public boolean showDurabilityBar(ItemStack itemStack) {
        return !(getEnergyStored() == getMaxEnergyStored());
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (double) (getEnergyStored() / getMaxEnergyStored());
    }

    public void onUpdate(ItemStack stack, World worldIn,Entity entityIn,int ItemSlot,boolean isSelected) {

    }
}
