package com.forestbat.warhammer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class EntityMachineBase extends TileEntity implements IEnergyStorage, ITickable, IItemHandler {
    private int spiritEnergyAmount,maxCapacity,maxReceive,maxExtract;
    public NonNullList<ItemStack> inv;

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Energy",getEnergyStored());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        if (!canReceive())
            return 0;
        int spiritEnergyAmountReceived = Math.min(maxCapacity - spiritEnergyAmount, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            spiritEnergyAmount += spiritEnergyAmountReceived;
        return spiritEnergyAmountReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        if (!canExtract())
            return 0;
        int spiritEnergyAmountExtracted = Math.min(spiritEnergyAmount, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            spiritEnergyAmount -= spiritEnergyAmountExtracted;
        return spiritEnergyAmountExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return spiritEnergyAmount;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return maxCapacity;
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

    public int getSizeInventory(){
        return this.inv.size();
    }
    public ItemStack getStackInSlot(int slot){
        return slot>this.getSizeInventory()?ItemStack.EMPTY:this.inv.get(slot);
    }
    public void setStackInSlot(ItemStack stack,int slot){
        if(slot<=inv.size())
            inv.set(slot,stack);
    }
    public ItemStack removeStackFromSlot(int slot){
        ItemStack stack=getStackInSlot(slot);
        setStackInSlot(stack,slot);
        return stack;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }

    public void update(){

    }
}









