package com.forestbat.warhammer.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static com.forestbat.warhammer.Warhammer.Items.*;
import static com.forestbat.warhammer.configs.WarhammerConfig.GENERATION_TICKS;

public class CrystalGenerator extends EntityMachineBase implements IItemHandler, ITickable {
    public static int index;
    public final int STACK_LIMIT=1024;
    public final int SIZE_INVENTORY=108;

    public int getSizeInventory(){
        return SIZE_INVENTORY;
    }
    public int getSlots(){
        return SIZE_INVENTORY;
    }
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack itemStack, boolean simulate){
        return new ItemStackHandler().insertItem(slot,itemStack,simulate);
    }
    @Nonnull
    public ItemStack extractItem(int slot,int amount,boolean simulate){
        return new ItemStackHandler().extractItem(slot,amount,simulate);
    }
    public int getSlotLimit(int slot){
        return STACK_LIMIT;
    }
    public void update(){
        if(this.world.getWorldTime()%GENERATION_TICKS==0)
            generateNewItem(index);
    }
    public void generateNewItem(int index){
        ItemStack itemStack=getStackInSlot(index);
        List<Item> crystalList= Lists.newArrayList
                (FireCrystal,WaterCrystal,WoodCrystal,GoldCrystal,SoilCrystal);
        if(crystalList.contains(itemStack.getItem())){
            itemStack.grow(3+new Random().nextInt(2));
        }
        markDirty();
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("generation",GENERATION_TICKS);
        for(index=0;index<SIZE_INVENTORY;index++) {
            ItemStack itemStack = getStackInSlot(index);
            if (getStackInSlot(index).getCount() > 0) {
                NBTTagCompound generation = new NBTTagCompound();
                itemStack.writeToNBT(generation);
                compound.setTag("generation", generation);
            }
        }
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
    }
}
