package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.items.itembombs.BombBase;
import com.forestbat.warhammer.items.itembombs.OreFinderBomb;
import com.forestbat.warhammer.items.itembombs.LensBombBase;
import com.forestbat.warhammer.items.itembombs.SkyRoarBomb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Turret extends EntityMachineBase{
    public static Item lensBombBase=new LensBombBase();
    public static ItemStack BOMB_ORE_FINDER_LEN=new ItemStack(lensBombBase,1,1);
    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemStack currentLen=super.getStackInSlot(slot);
        return ItemStack.EMPTY;
    }
    public BombBase getBomb(int meta){
        //todo
        switch (meta){
            case 1:return new OreFinderBomb();
            case 2:return new SkyRoarBomb();
            default:return null;
        }
    }
}
