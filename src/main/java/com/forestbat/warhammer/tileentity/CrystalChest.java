package com.forestbat.warhammer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class CrystalChest extends TileEntity implements IItemHandler {
    public int getSlots(){
        return 144;
    }

    @Nonnull
    public ItemStack getStackInSlot(int slot){
        return ItemStack.EMPTY;
    }
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
        return ItemStack.EMPTY;
    }

    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate){
        return ItemStack.EMPTY;
    }

    public int getSlotLimit(int slot){
        return 128;
    }
}
