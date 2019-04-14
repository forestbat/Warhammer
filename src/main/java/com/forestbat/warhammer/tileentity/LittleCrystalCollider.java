package com.forestbat.warhammer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static com.forestbat.warhammer.Warhammer.*;
import static com.forestbat.warhammer.Warhammer.Items.*;

public class LittleCrystalCollider extends EntityMachineBase {
    private ItemStack crystalFirst=getStackInSlot(0);
    private ItemStack crystalSecond=getStackInSlot(1);
    private int FirstAmount=crystalFirst.getCount();
    private int SecondAmount=crystalSecond.getCount();
    public int differentCrystalPower(){
        //todo too many if
        if(crystalFirst== FireCrystal.getDefaultInstance() && crystalSecond== WaterCrystal.getDefaultInstance())
            return 100;
        if(crystalFirst== FireCrystal.getDefaultInstance() && crystalSecond==WoodCrystal.getDefaultInstance())
            return 78;
        if(crystalFirst== FireCrystal.getDefaultInstance() && crystalSecond== GoldCrystal.getDefaultInstance())
            return 94;
        if(crystalFirst== FireCrystal.getDefaultInstance() &&crystalSecond== FireCrystal.getDefaultInstance())
            return 120;
        if(crystalFirst== FireCrystal.getDefaultInstance() &&crystalSecond== SoilCrystal.getDefaultInstance())
            return 60;
        if(crystalFirst== GoldCrystal.getDefaultInstance() &&crystalSecond==WoodCrystal.getDefaultInstance())
            return 88;
        else return 0;
    }

    @Override
    public void update() {
        if(crystalFirst.getCount()>0 && crystalSecond.getCount()>0) {
            receiveEnergy(differentCrystalPower(), false);
            if(world.getWorldTime()%100==0) {
                crystalFirst.shrink(1);
                crystalSecond.shrink(1);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("crystalInFirst",FirstAmount);
        compound.setInteger("crystalInSecond",SecondAmount);
        return super.writeToNBT(compound);
    }
}
