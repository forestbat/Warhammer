package com.forestbat.warhammer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static com.forestbat.warhammer.Warhammer.Items.FIRE_CRYSTAL;

public class LittleCrystalBurner extends EntityMachineBase {
    private int BURNER_MAX_STORE=50000;
    private ItemStack crystalStack=getStackInSlot(0);
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("crystalamount",crystalStack.getCount());
        return super.writeToNBT(compound);
    }
    @Override
    public boolean canExtract() {
        return true;
    }
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
    }
    public void update(){
        if(crystalStack== FIRE_CRYSTAL.getDefaultInstance() &&crystalStack.getCount()>0) {
            receiveEnergy(5,false);
            if(world.getWorldTime()%100==0) {
                crystalStack.shrink(1);
            }
        }
    }

    @Override
    public int getMaxEnergyStored() {
        return BURNER_MAX_STORE;
    }
}
